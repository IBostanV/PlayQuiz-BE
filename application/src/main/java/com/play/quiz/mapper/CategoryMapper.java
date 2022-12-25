package com.play.quiz.mapper;

import com.play.quiz.dto.CategoryDto;
import com.play.quiz.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto toDto(final Category category);

    Category toEntity(final CategoryDto categoryDto);
}
