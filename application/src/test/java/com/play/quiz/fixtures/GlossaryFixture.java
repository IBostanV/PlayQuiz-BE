package com.play.quiz.fixtures;

import com.play.quiz.model.Glossary;

import java.util.Arrays;
import java.util.List;

public class GlossaryFixture {

    public static Glossary getGlossary() {
        return defaultGlossary(1L, "Life", "Power");
    }

    public static List<Glossary> getGlossaryList() {
        Glossary glossary1 = defaultGlossary(1L, "Life", "Power");
        Glossary glossary2 = defaultGlossary(1L, "Power", "Life");
        Glossary glossary3 = defaultGlossary(1L, "Words", "Thoughts");

        return Arrays.asList(glossary1, glossary2, glossary3);
    }

    private static Glossary defaultGlossary(final Long id, final String key, final String value) {
        return Glossary.builder()
                .termId(id)
                .key(key)
                .value(value)
                .category(CategoryFixture.getCategory())
                .build();
    }
}
