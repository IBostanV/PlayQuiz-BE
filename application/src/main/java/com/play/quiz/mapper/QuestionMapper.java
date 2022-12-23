package com.play.quiz.mapper;

import com.play.quiz.dto.QuestionDto;
import com.play.quiz.model.Question;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionDto mapToDto(final Question question);

    List<QuestionDto> mapToDtoList(final List<Question> questionList);

    Question mapToEntity(final QuestionDto questionDto);
}
