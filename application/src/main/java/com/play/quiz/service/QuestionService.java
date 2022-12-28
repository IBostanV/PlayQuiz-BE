package com.play.quiz.service;

import com.play.quiz.dto.QuestionDto;
import com.play.quiz.enums.CategoryType;

import java.util.List;

public interface QuestionService {

    QuestionDto save(final QuestionDto questionDto);

    List<QuestionDto> findAll();

    List<QuestionDto> findByCategoryNaturalId(final CategoryType categoryType);

    void deleteById(final Long questionId);
}
