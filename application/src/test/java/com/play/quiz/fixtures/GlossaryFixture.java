package com.play.quiz.fixtures;

import java.util.Arrays;
import java.util.List;

import com.play.quiz.dto.GlossaryDto;
import com.play.quiz.domain.Glossary;

public class GlossaryFixture {

    public static Glossary getGlossary() {
        return defaultGlossary("Life", "Power");
    }

    public static List<Glossary> getGlossaryList() {
        Glossary glossary1 = defaultGlossary("Life", "Power");
        Glossary glossary2 = defaultGlossary("Power", "Life");
        Glossary glossary3 = defaultGlossary("Words", "Thoughts");

        return Arrays.asList(glossary1, glossary2, glossary3);
    }

    public static Glossary defaultGlossary(String key, String value) {
        return Glossary.builder()
                .termId(1L)
                .key(key)
                .value(value)
                .category(CategoryFixture.getCategory())
                .build();
    }

    public static GlossaryDto getGlossaryDto() {
        return GlossaryDto.builder()
                .termId(1L)
                .key("Life")
                .value("Power")
                .categoryId(CategoryFixture.getCategoryDto().getCatId())
                .categoryName(CategoryFixture.getCategoryDto().getName())
                .build();
    }
}
