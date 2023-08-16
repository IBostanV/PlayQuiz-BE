package com.play.quiz.dto;

import java.util.List;

import com.play.quiz.dto.translation.AnswerTranslationDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AnswerDto {
    Long id;
    String content;
    GlossaryDto glossary;
    QuestionDto question;
    List<AnswerTranslationDto> answerTranslations;
}
