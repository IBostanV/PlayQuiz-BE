package com.play.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDto {
   Long catId;

   @NotBlank String name;

   String naturalId;

   CategoryDto parent;
}
