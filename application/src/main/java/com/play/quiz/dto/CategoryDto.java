package com.play.quiz.dto;

import java.util.List;

import com.play.quiz.dto.translation.CategoryTranslationDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
   private Long catId;
   private Long parentId;
   private Boolean visible;
   private String naturalId;
   private String parentName;
   private List<CategoryTranslationDto> categoryTranslations;

   @NotBlank
   private String name;
}
