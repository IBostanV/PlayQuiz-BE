package com.play.quiz.mapper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.play.quiz.dto.GlossaryDto;
import com.play.quiz.model.Glossary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public interface GlossaryMapper {

    @Mapping(target = "parent.attachment", ignore = true)
    @Mapping(source = "isActive", target = "isActive")
    GlossaryDto toDto(final Glossary glossary);

    @Mapping(target = "parent.attachment", ignore = true)
    @Mapping(source = "glossary.isActive", target = "isActive")
    @Mapping(source = "attachment", target = "attachment", qualifiedByName = "handleAttachment")
    GlossaryDto toDto(final Glossary glossary, final MultipartFile attachment);

    @Mapping(target = "parent.attachment", ignore = true)
    @Mapping(target = "glossaryTranslations", ignore = true)
    @Mapping(source = "glossaryDto.isActive", target = "isActive")
    @Mapping(source = "attachment", target = "attachment", qualifiedByName = "handleAttachment")
    Glossary toEntity(final GlossaryDto glossaryDto, final MultipartFile attachment);

    List<GlossaryDto> toDto(final List<Glossary> glossaries);

    @Named("handleAttachment")
    default byte[] handleAttachment(final MultipartFile attachment) throws IOException {
        return Objects.nonNull(attachment) ? attachment.getBytes() : null;
    }
}
