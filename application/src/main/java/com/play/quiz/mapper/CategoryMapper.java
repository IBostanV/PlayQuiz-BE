package com.play.quiz.mapper;

import com.play.quiz.dto.CategoryDto;
import com.play.quiz.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "naturalId", source = "name", qualifiedByName = "upperCase")
    CategoryDto toDto(final Category category);

    @Mapping(target = "naturalId", source = "name", qualifiedByName = "upperCase")
    Category toEntity(final CategoryDto categoryDto);

    @Named("upperCase")
    default String upperCase(final String naturalId) {
        return naturalId.toUpperCase().replace(" ", "_");
    }
}
