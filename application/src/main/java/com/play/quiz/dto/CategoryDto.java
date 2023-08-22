package com.play.quiz.dto;

import java.util.List;

import com.play.quiz.dto.translation.CategoryTranslationDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDto {
   Long catId;
   Boolean visible;
   String naturalId;
   CategoryDto parent;
   @NotBlank String name;
   List<CategoryTranslationDto> categoryTranslations;
}
