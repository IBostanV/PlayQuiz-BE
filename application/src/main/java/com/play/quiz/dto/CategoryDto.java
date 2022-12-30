package com.play.quiz.dto;

import com.play.quiz.model.Category;
import lombok.Builder;
import lombok.Value;

import jakarta.validation.constraints.NotBlank;

@Value
@Builder
public class CategoryDto {
   Long catId;

   @NotBlank String name;

   String naturalId;

   Category parent;
}
