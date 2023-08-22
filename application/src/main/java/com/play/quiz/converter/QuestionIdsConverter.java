package com.play.quiz.converter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class QuestionIdsConverter implements AttributeConverter<Set<Long>, String> {
    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(final Set<Long> questionIds) {
        String listAsString = questionIds.toString();
        return String.join(SPLIT_CHAR, listAsString.substring(1, listAsString.length() - 1));
    }

    @Override
    public Set<Long> convertToEntityAttribute(final String databaseIds) {
        List<String> stringList = Arrays.asList(databaseIds.split(SPLIT_CHAR));
        return stringList.stream().map(String::trim).map(Long::parseLong).collect(Collectors.toSet());
    }
}
