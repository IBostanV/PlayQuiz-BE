package com.play.quiz.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AnswerDto {
    Long id;

    QuestionDto question;

    String content;

    GlossaryDto glossary;
}
