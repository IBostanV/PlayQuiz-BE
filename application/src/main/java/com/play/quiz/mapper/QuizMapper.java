package com.play.quiz.mapper;

import com.play.quiz.dto.QuizDto;
import com.play.quiz.model.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuizMapper {

    Quiz toEntity(final QuizDto quizDto);

    @Mapping(target = "quizType", ignore = true)
    QuizDto toDto(final Quiz quiz);
}
