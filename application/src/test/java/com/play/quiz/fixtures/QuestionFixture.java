package com.play.quiz.fixtures;

import com.play.quiz.enums.QuestionAttribute;
import com.play.quiz.enums.QuestionType;
import com.play.quiz.model.Answer;
import com.play.quiz.model.Question;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class QuestionFixture {

    public static Question getGeneratedQuestion(final Long id, final String placeholder) {
        Question.QuestionBuilder questionBuilder = Question.builder()
                .questionId(id)
                .type(QuestionType.GENERATED)
                .attributes(Collections.emptyList())
                .category(CategoryFixture.getCategory())
                .content("What is the meaning of "+ placeholder +"?");
        Answer answer = AnswerFixture
                .getAnswer(null, questionBuilder.build(), "What is the meaning of Life?");

        return questionBuilder
                .answers(Collections.singletonList(answer))
                .build();
    }

    public static Question getTemplateQuestion(final Long id) {
        return Question.builder()
                .questionId(id)
                .category(CategoryFixture.getCategory())
                .content("What is the meaning of %s?")
                .type(QuestionType.TEMPLATE)
                .build();
    }

    public static Question getAttributeTemplateQuestion(final Long id) {
        return Question.builder()
                .questionId(id)
                .category(CategoryFixture.getCategory())
                .content("What is the meaning of %s?")
                .type(QuestionType.TEMPLATE)
                .attributes(Collections.singletonList(QuestionAttribute.ANSWER_BY_KEY))
                .build();
    }

    public static List<Question> getTemplateQuestionList() {
        return LongStream.range(1, 10)
                .mapToObj(QuestionFixture::getTemplateQuestion)
                .collect(Collectors.toList());
    }

    public static List<Question> getAttributeTemplateQuestionList() {
        return LongStream.range(1, 10)
                .mapToObj(QuestionFixture::getAttributeTemplateQuestion)
                .collect(Collectors.toList());
    }
}
