package com.play.quiz.service;

import com.play.quiz.dto.CategoryDto;

public interface CategoryService {

    CategoryDto save(final CategoryDto category);

    CategoryDto getById(final Long categoryId);

    CategoryDto getByNaturalId(final String categoryName);

    void deleteById(final Long categoryId);
}
