package com.play.quiz.dto;

import java.util.List;

import com.play.quiz.dto.translation.GlossaryTranslationDto;
import com.play.quiz.domain.GlossaryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GlossaryDto {
    Long termId;
    String value;
    String options;
    Boolean isActive;
    byte[] attachment;
    GlossaryType type;
    GlossaryDto parent;
    @NotBlank String key;
    @NotNull CategoryDto category;
    List<GlossaryTranslationDto> glossaryTranslations;
}
