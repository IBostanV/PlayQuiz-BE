package com.play.quiz.dto.translation;

import com.play.quiz.domain.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTranslationDto {
    private String name;
    private Long translId;
    private Language language;
    private String description;
}
