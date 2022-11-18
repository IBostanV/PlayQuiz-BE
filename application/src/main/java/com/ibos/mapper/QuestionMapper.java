package com.ibos.mapper;

import com.ibos.dto.QuestionDto;
import com.ibos.model.Question;

import java.util.List;

public interface QuestionMapper {
    QuestionDto mapToDto(final Question question);

    List<QuestionDto> mapToDtoList(final List<Question> questionList);

    Question mapToEntity(final QuestionDto questionDto);
}
