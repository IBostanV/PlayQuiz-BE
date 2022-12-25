package com.play.quiz.mapper;

import com.play.quiz.dto.GlossaryDto;
import com.play.quiz.model.Glossary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GlossaryMapper {

    GlossaryMapper INSTANCE = Mappers.getMapper(GlossaryMapper.class);

    @Mapping(target = "parent.attachment", ignore = true)
    GlossaryDto toDto(final Glossary glossary);

    @Mapping(target = "parent.attachment", ignore = true)
    @Mapping(source = "attachment", target = "attachment", qualifiedByName = "handleAttachment")
    GlossaryDto toDto(final Glossary glossary, final MultipartFile attachment);

    @Mapping(target = "parent.attachment", ignore = true)
    @Mapping(source = "attachment", target = "attachment", qualifiedByName = "handleAttachment")
    Glossary toEntity(final GlossaryDto glossaryDto, final MultipartFile attachment);

    List<GlossaryDto> toDto(final List<Glossary> glossaries);

    @Named("handleAttachment")
    default byte[] mapAttachment(final MultipartFile attachment) throws IOException {
        return attachment.getBytes();
    }
}
