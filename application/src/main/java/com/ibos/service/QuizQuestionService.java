package com.ibos.service;

import com.ibos.dto.QuestionDto;
import com.ibos.enums.QuestionCategory;

import java.util.List;

public interface QuizQuestionService {
    List<QuestionDto> findAll();

    List<QuestionDto> getCategoryQuestions(QuestionCategory category);
}
