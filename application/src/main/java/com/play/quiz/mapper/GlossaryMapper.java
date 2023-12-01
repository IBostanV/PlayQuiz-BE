package com.play.quiz.mapper;

import java.util.List;
import java.util.Objects;

import com.play.quiz.domain.Glossary;
import com.play.quiz.dto.GlossaryDto;
import lombok.SneakyThrows;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public interface GlossaryMapper {

    @Mapping(source = "isActive", target = "isActive")
    @Mapping(target = "parentKey", source = "parent.key")
    @Mapping(target = "parentId", source = "parent.termId")
    @Mapping(target = "parentValue", source = "parent.value")
    @Mapping(target = "categoryId", source = "category.catId")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "glossaryTranslations", ignore = true)
    GlossaryDto toDto(final Glossary glossary);

    @Mapping(source = "isActive", target = "isActive")
    @Mapping(target = "parentKey", source = "parent.key")
    @Mapping(target = "parentId", source = "parent.termId")
    @Mapping(target = "parentValue", source = "parent.value")
    @Mapping(target = "categoryId", source = "category.catId")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "attachment", expression = "java(handleAttachment(attachment))")
    GlossaryDto toDto(final Glossary glossary, @Context final MultipartFile attachment);

    @Mapping(source = "isActive", target = "isActive")
    @Mapping(target = "glossaryTranslations", ignore = true)
    @Mapping(target = "category.catId", source = "categoryId")
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "handleParent")
    @Mapping(target = "attachment", expression = "java(handleAttachment(attachment))")
    Glossary toEntity(final GlossaryDto glossaryDto, @Context final MultipartFile attachment);

    List<GlossaryDto> toDto(final List<Glossary> glossaries);

    @SneakyThrows
    @Named("handleAttachment")
    default byte[] handleAttachment(final MultipartFile attachment) {
        return Objects.nonNull(attachment) ? attachment.getBytes() : null;
    }

    @Named("handleParent")
    default Glossary handleParent(final Long parentId) {
        if (Objects.isNull(parentId)) return null;
        return Glossary.builder().termId(parentId).build();
    }
}
