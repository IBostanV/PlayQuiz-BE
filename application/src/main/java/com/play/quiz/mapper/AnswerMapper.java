package com.play.quiz.mapper;

import com.play.quiz.dto.AnswerDto;
import com.play.quiz.model.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    @Mapping(target = "ansId", source = "id")
    Answer toEntity(final AnswerDto answerDto);

    @Mapping(target = "id", source = "ansId")
    AnswerDto toDto(final Answer answer);
}
