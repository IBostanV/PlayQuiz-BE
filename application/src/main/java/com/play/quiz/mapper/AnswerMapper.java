package com.play.quiz.mapper;

import java.util.List;

import com.play.quiz.dto.AnswerDto;
import com.play.quiz.model.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class AnswerMapper {

    @Autowired private GlossaryMapper glossaryMapper;

    @Mapping(target = "ansId", source = "id")
    public abstract Answer toEntity(final AnswerDto answerDto);

    @Mapping(target = "id", source = "ansId")
    public abstract AnswerDto toDto(final Answer answer);

    public abstract List<AnswerDto> toDtoList(final List<Answer> answerList);

    public abstract List<Answer> toEntityList(final List<AnswerDto> answerList);
}
