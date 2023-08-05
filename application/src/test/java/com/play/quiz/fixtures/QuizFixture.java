package com.play.quiz.fixtures;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.play.quiz.dto.QuizDto;
import com.play.quiz.enums.QuizType;
import com.play.quiz.model.Question;
import com.play.quiz.model.Quiz;

public class QuizFixture {
    public static QuizDto getQuizNoQuestionDto() {
        return QuizDto.builder()
                .quizId(1L)
                .quizType(QuizType.SINGLE_CHOICE)
                .category(CategoryFixture.getCategoryDto())
                .questionsCount(5)
                .build();
    }

    public static Quiz getQuiz() {
        return Quiz.builder()
                .quizId(1L)
                .category(CategoryFixture.getCategory())
                .questionsCount(5)
                .questionIds(Collections.singleton(1L))
                .questionList(getQuestionList())
                .build();
    }

    private static List<Question> getQuestionList() {
        return IntStream.range(0, 5)
                .mapToObj(i -> QuestionFixture.getGeneratedQuestion(1L, "Life"))
                .collect(Collectors.toList());
    }
}
