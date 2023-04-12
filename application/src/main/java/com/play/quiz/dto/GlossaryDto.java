package com.play.quiz.dto;

import com.play.quiz.dto.translation.GlossaryTranslationDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class GlossaryDto {

    Long termId;

    @NotBlank String key;

    String value;

    @NotNull CategoryDto category;

    byte[] attachment;

    String options;

    GlossaryDto parent;

    boolean isActive;

    List<GlossaryTranslationDto> glossaryTranslations;
}
