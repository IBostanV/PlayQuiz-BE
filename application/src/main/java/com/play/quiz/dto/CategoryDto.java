package com.play.quiz.dto;

import com.play.quiz.model.Category;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDto {
   Long catId;

   String name;

   String naturalId;

   Category parent;
}
