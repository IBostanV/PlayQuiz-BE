package com.play.quiz.converter;

import com.play.quiz.enums.QuestionAttribute;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Converter
public class AttributeListConverter implements AttributeConverter<List<QuestionAttribute>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(final List<QuestionAttribute> attributes) {
        List<String> attributesStringList = attributes.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
        return String.join(SPLIT_CHAR, attributesStringList);
    }

    @Override
    public List<QuestionAttribute> convertToEntityAttribute(String attributesColumn) {
        if (Objects.isNull(attributesColumn)) {
            return Collections.emptyList();
        }

        List<String> stringList = Arrays.asList(attributesColumn.split(SPLIT_CHAR));
        return stringList.stream()
                .map(QuestionAttribute::valueOf)
                .collect(Collectors.toList());
    }
}
