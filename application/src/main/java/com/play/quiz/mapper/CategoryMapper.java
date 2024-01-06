package com.play.quiz.mapper;

import java.util.List;
import java.util.Objects;

import com.play.quiz.dto.CategoryDto;
import com.play.quiz.domain.Category;
import lombok.SneakyThrows;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "categoryTranslations", ignore = true)
    @Mapping(target = "parentId", source = "parent.catId")
    @Mapping(target = "parentName", source = "parent.name")
    @Mapping(target = "naturalId", source = "name", qualifiedByName = "upperCase")
    CategoryDto toDto(final Category category);

    List<CategoryDto> toDtoList(final List<Category> categories);

    @Mapping(target = "naturalId", source = "name", qualifiedByName = "upperCase")
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "handleParent")
    @Mapping(target = "attachment", expression = "java(handleAttachment(attachment))")
    Category toEntity(final CategoryDto categoryDto, @Context final MultipartFile attachment);

    @Named("upperCase")
    default String upperCase(String naturalId) {
        Assert.notNull(naturalId, "Natural id of category cannot be null");
        return naturalId.toUpperCase().replace(" ", "_");
    }

    @Named("handleParent")
    default Category handleParent(final Long catId) {
        if (Objects.nonNull(catId)) {
            return Category.builder().catId(catId).build();
        }
        return null;
    }

    @SneakyThrows
    @Named("handleAttachment")
    default byte[] handleAttachment(final MultipartFile attachment) {
        return Objects.nonNull(attachment) ? attachment.getBytes() : null;
    }
}
