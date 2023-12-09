package com.play.quiz.fixtures;

import com.play.quiz.dto.CategoryDto;
import com.play.quiz.domain.Category;

public class CategoryFixture {

    public static Category getSimpleCategory() {
        return Category.builder()
                .catId(3L)
                .build();
    }

    public static Category getCategory() {
        return Category.builder()
                .catId(3L)
                .visible(true)
                .name("Continent")
                .parent(getParent())
                .naturalId("CONTINENT")
                .build();
    }

    public static Category getParent() {
        return Category.builder()
                .catId(2L)
                .name("Earth")
                .naturalId("EARTH")
                .build();
    }

    public static CategoryDto getParentNoIdDto() {
        return CategoryDto.builder()
                .name("Earth")
                .naturalId("EARTH")
                .build();
    }

    public static CategoryDto getNoIdCategoryDto() {
        return CategoryDto.builder()
                .name("Continent")
                .visible(true)
                .parentName("Earth")
                .naturalId("CONTINENT")
                .build();
    }

    public static CategoryDto getNoIdParentCategoryDto() {
        return CategoryDto.builder()
                .name("Continent")
                .visible(true)
                .parentId(1L)
                .parentName("Earth")
                .naturalId("CONTINENT")
                .build();
    }

    public static CategoryDto getCategoryDto() {
        return CategoryDto.builder()
                .catId(3L)
                .name("Continent")
                .visible(true)
                .parentId(2L)
                .parentName("Earth")
                .naturalId("CONTINENT")
                .build();
    }

    public static CategoryDto getNewCategory() {
        return CategoryDto.builder()
                .catId(1L)
                .name("Ocean")
                .visible(true)
                .parentId(2L)
                .naturalId("OCEAN")
                .build();
    }
}
