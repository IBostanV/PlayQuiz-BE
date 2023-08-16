package com.play.quiz.fixtures;

import com.play.quiz.dto.AnswerDto;
import com.play.quiz.model.Answer;
import com.play.quiz.model.Question;

public class AnswerFixture {

    public static Answer getAnswer(final Long id, final Question question, final String content) {
        return Answer.builder()
                .ansId(id)
                .question(question)
                .content(content)
                .glossary(GlossaryFixture.getGlossary())
                .build();
    }

    public static Answer getAnswerNoQuestion() {
        return Answer.builder()
                .ansId(26L)
                .content("Power")
                .glossary(GlossaryFixture.getGlossary())
                .build();
    }

    public static AnswerDto getAnswerDtoNoQuestion() {
        return AnswerDto.builder()
                .id(26L)
                .content("Power")
                .glossary(GlossaryFixture.getGlossaryDto())
                .build();
    }
}
