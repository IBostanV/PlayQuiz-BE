package com.play.quiz.mapper;

import com.play.quiz.dto.QuizDto;
import com.play.quiz.model.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuizMapper {

    QuizMapper INSTANCE = Mappers.getMapper(QuizMapper.class);

    Quiz toEntity(final QuizDto quizDto);

    @Mapping(target = "quizType", ignore = true)
    QuizDto toDto(final Quiz quiz);
}
