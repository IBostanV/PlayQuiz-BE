package com.play.quiz.dto;

import java.util.List;

import com.play.quiz.dto.translation.AnswerTranslationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {
    private Long id;
    private Long termId;
    private String content;
    private byte[] glossaryAttachment;
    private List<AnswerTranslationDto> answerTranslations;
}
