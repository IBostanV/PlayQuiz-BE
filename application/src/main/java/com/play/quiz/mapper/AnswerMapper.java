package com.play.quiz.mapper;

import com.play.quiz.dto.AnswerDto;
import com.play.quiz.model.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    @Mapping(target = "ansId", source = "id")
    Answer toEntity(final AnswerDto answerDto);

    @Mapping(target = "id", source = "ansId")
    AnswerDto toDto(final Answer answer);
}
