package com.play.quiz.service;

import java.util.List;

import com.play.quiz.dto.CategoryDto;

public interface CategoryService {

    CategoryDto save(final CategoryDto category);

    CategoryDto getById(final Long categoryId);

    CategoryDto getByNaturalId(String categoryName);

    void deleteById(final Long categoryId);

    List<CategoryDto> getCategories();
}
