package com.play.quiz.mapper;

import java.util.List;

import com.play.quiz.dto.CategoryDto;
import com.play.quiz.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "categoryTranslations", ignore = true)
    @Mapping(target = "parent.categoryTranslations", ignore = true)
    @Mapping(target = "naturalId", source = "name", qualifiedByName = "upperCase")
    CategoryDto toDto(final Category category);

    List<CategoryDto> toDtoList(final List<Category> categories);

    @Mapping(target = "naturalId", source = "name", qualifiedByName = "upperCase")
    Category toEntity(final CategoryDto categoryDto);

    @Named("upperCase")
    default String upperCase(final String naturalId) {
        return naturalId.toUpperCase().replace(" ", "_");
    }
}
