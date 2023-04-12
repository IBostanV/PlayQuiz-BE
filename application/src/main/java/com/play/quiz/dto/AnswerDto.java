package com.play.quiz.dto;

import com.play.quiz.dto.translation.AnswerTranslationDto;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class AnswerDto {
    Long id;

    QuestionDto question;

    String content;

    GlossaryDto glossary;

    List<AnswerTranslationDto> answerTranslations;
}
