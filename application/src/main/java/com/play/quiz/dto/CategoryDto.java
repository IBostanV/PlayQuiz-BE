package com.play.quiz.dto;

import com.play.quiz.dto.translation.CategoryTranslationDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CategoryDto {
   Long catId;

   @NotBlank String name;

   String naturalId;

   CategoryDto parent;

   List<CategoryTranslationDto> categoryTranslations;
}
