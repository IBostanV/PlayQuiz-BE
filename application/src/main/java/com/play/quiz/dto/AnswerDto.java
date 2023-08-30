package com.play.quiz.dto;

import java.util.List;

import com.play.quiz.dto.translation.AnswerTranslationDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class AnswerDto {
    private Long id;
    private String content;
    private GlossaryDto glossary;
    private QuestionDto question;
    private List<AnswerTranslationDto> answerTranslations;

    public static AnswerDto withGlossary(final GlossaryDto glossaryDto) {
        return new AnswerDto(null, glossaryDto.getValue(), glossaryDto, null, null);
    }
}
