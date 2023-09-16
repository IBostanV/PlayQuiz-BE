package com.play.quiz.fixtures;

import com.play.quiz.dto.AnswerDto;
import com.play.quiz.domain.Answer;
import com.play.quiz.domain.Question;

public class AnswerFixture {

    public static Answer getAnswer(final Long id, final Question question, String content) {
        return Answer.builder()
                .ansId(id)
                .question(question)
                .content(content)
                .glossary(GlossaryFixture.getGlossary())
                .build();
    }

    public static Answer getAnswerNoQuestion() {
        return Answer.builder()
                .ansId(1L)
                .content("Power")
                .glossary(GlossaryFixture.getGlossary())
                .build();
    }

    public static AnswerDto getAnswerDtoNoQuestion() {
        return AnswerDto.builder()
                .id(1L)
                .content("Power")
                .termId(GlossaryFixture.getGlossaryDto().getTermId())
                .glossaryAttachment(GlossaryFixture.getGlossaryDto().getAttachment())
                .build();
    }

    public static AnswerDto getAnswerDtoNoQuestionNoId() {
        return AnswerDto.builder()
                .content("What is the meaning of Life?")
                .termId(GlossaryFixture.getGlossaryDto().getTermId())
                .glossaryAttachment(GlossaryFixture.getGlossaryDto().getAttachment())
                .build();
    }
}
