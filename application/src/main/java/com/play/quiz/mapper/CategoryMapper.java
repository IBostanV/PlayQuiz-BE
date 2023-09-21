package com.play.quiz.mapper;

import java.util.List;
import java.util.Objects;

import com.play.quiz.dto.CategoryDto;
import com.play.quiz.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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
    Category toEntity(final CategoryDto categoryDto);

    @Named("upperCase")
    default String upperCase(String naturalId) {
        return naturalId.toUpperCase().replace(" ", "_");
    }

    @Named("handleParent")
    default Category handleParent(final Long catId) {
        if (Objects.nonNull(catId)) {
            return Category.builder().catId(catId).build();
        }
        return null;
    }
}
