package com.play.quiz.dto.translation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.play.quiz.dto.QuestionDto;
import com.play.quiz.domain.Language;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionTranslationDto {
    String name;
    Long translId;
    Language language;
    String description;
    QuestionDto question;
}
