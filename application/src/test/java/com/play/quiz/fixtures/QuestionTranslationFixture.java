package com.play.quiz.fixtures;

import com.play.quiz.dto.translation.QuestionTranslationDto;
import com.play.quiz.domain.Language;
import com.play.quiz.domain.translation.QuestionTranslation;

public class QuestionTranslationFixture {
    public static QuestionTranslation getQuestionTranslation() {
        return QuestionTranslation.builder()
                .translId(1L)
                .name("What is the meaning of Life?")
                .description("What is the meaning of Life?")
                .language(Language.builder().langId(1L).langCode("EN").name("English").build())
                .build();
    }

    public static QuestionTranslationDto getQuestionTranslationDto() {
        return QuestionTranslationDto.builder()
                .translId(1L)
                .name("What is the meaning of Life?")
                .description("What is the meaning of Life?")
                .language(Language.builder().langId(1L).langCode("EN").name("English").build())
                .build();
    }
}
