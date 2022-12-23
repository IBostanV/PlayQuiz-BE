package com.play.quiz.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
}
