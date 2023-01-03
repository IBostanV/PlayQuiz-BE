package com.play.quiz.service.impl;

import com.play.quiz.dto.CategoryDto;
import com.play.quiz.exception.RecordNotFoundException;
import com.play.quiz.mapper.CategoryMapper;
import com.play.quiz.model.Category;
import com.play.quiz.repository.CategoryRepository;
import com.play.quiz.service.CategoryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto save(final CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        return categoryMapper.INSTANCE.toDto(categoryRepository.save(category));
    }

    @NonNull
    @Override
    public CategoryDto getById(final Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RecordNotFoundException("No records found by category id: " + categoryId));
        return categoryMapper.INSTANCE.toDto(category);
    }

    @NonNull
    @Override
    public CategoryDto getByNaturalId(final String naturalId) {
        Category category = categoryRepository.findByNaturalId(naturalId)
                .orElseThrow(() -> new RecordNotFoundException("No records found by natural Id: " + naturalId));
        return categoryMapper.INSTANCE.toDto(category);
    }

    @Override
    public void deleteById(final Long categoryId) {
        log.info("Deleting category with id: "+ categoryId);
        categoryRepository.deleteById(categoryId);
    }
}
