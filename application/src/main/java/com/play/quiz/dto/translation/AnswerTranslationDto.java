package com.play.quiz.dto.translation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.play.quiz.dto.AnswerDto;
import com.play.quiz.model.Language;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerTranslationDto {

    Long transId;

    String name;

    String description;

    Language language;

    AnswerDto answer;
}
