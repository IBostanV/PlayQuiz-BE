package com.play.quiz.service;

import com.play.quiz.dto.QuestionDto;
import com.play.quiz.model.Category;

import java.util.List;

public interface QuizQuestionService {

    List<QuestionDto> findAll();

    List<QuestionDto> getCategoryQuestions(final Category category);
}
