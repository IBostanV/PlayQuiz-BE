package com.play.quiz.service;

import com.play.quiz.dto.QuizDto;

public interface QuizService {

    QuizDto create(final QuizDto quizDto);

    QuizDto getById(final Long quizId);

    QuizDto getExpressQuiz();
}
