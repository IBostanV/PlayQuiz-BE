package com.play.quiz.dto;

import com.play.quiz.dto.translation.CategoryTranslationDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CategoryDto {
   private Long catId;
   private Long parentId;
   private Boolean visible;
   private String naturalId;
   private byte[] attachment;
   private String parentName;
   private List<CategoryTranslationDto> categoryTranslations;

   @NotBlank
   private String name;
}
