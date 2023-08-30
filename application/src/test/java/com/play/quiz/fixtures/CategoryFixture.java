package com.play.quiz.fixtures;

import com.play.quiz.dto.CategoryDto;
import com.play.quiz.domain.Category;

public class CategoryFixture {

    public static Category getSimpleCategory() {
        return Category.builder()
                .catId(7L)
                .build();
    }

    public static Category getCategory() {
        return Category.builder()
                .catId(7L)
                .name("Continent")
                .naturalId("CONTINENT")
                .build();
    }

    public static CategoryDto getCategoryDto() {
        return CategoryDto.builder()
                .catId(7L)
                .name("Continent")
                .naturalId("CONTINENT")
                .build();
    }
}
