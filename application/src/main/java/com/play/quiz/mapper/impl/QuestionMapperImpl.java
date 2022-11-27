package com.play.quiz.mapper.impl;

import com.play.quiz.dto.QuestionDto;
import com.play.quiz.mapper.QuestionMapper;
import com.play.quiz.model.Question;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapperImpl implements QuestionMapper {
    @Override
    public QuestionDto mapToDto(final Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .category(question.getCategory())
                .content(question.getContent())
                .difficulty(question.getDifficulty())
                .priority(question.getPriority())
                .topic(question.getTopic())
                .build();
    }

    @Override
    public List<QuestionDto> mapToDtoList(List<Question> questionList) {
        return questionList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Question mapToEntity(final QuestionDto questionDto) {
        return Question.builder()
                .id(questionDto.getId())
                .category(questionDto.getCategory())
                .content(questionDto.getContent())
                .difficulty(questionDto.getDifficulty())
                .priority(questionDto.getPriority())
                .topic(questionDto.getTopic())
                .build();
    }
}
