package com.play.quiz.fixtures;

import com.play.quiz.model.Category;

public class CategoryFixture {

    public static Category getCategory() {
        return Category.builder()
                .catId(7L)
                .name("Continent")
                .naturalId("CONTINENT")
                .build();
    }
}
