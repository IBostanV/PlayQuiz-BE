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
                .visible(true)
                .name("Continent")
                .parent(getParent())
                .naturalId("CONTINENT")
                .build();
    }

    public static Category getParent() {
        return Category.builder()
                .catId(1L)
                .name("Earth")
                .naturalId("EARTH")
                .build();
    }

    public static CategoryDto getCategoryDto() {
        return CategoryDto.builder()
                .catId(7L)
                .name("Continent")
                .visible(true)
                .parentId(1L)
                .parentName("Earth")
                .naturalId("CONTINENT")
                .build();
    }
}
