package com.play.quiz.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class QuestionIdsConverter implements AttributeConverter<Set<Long>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(final Set<Long> attributes) {
        String listAsString = attributes.toString();
        return String.join(SPLIT_CHAR, listAsString.substring(1, listAsString.length() - 1));
    }

    @Override
    public Set<Long> convertToEntityAttribute(final String ids) {
        List<String> stringList = Arrays.asList(ids.split(SPLIT_CHAR));
        return stringList.stream().map(Long::parseLong).collect(Collectors.toSet());
    }
}
