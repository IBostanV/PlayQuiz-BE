package com.play.quiz.fixtures;

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
}
