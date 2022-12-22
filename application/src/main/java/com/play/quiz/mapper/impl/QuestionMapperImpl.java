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
                .type(question.getType())
                .topic(question.getTopic())
                .tipId(question.getTipId())
                .isActive(question.isActive())
                .answers(question.getAnswers())
                .account(question.getAccount())
                .content(question.getContent())
                .category(question.getCategory())
                .priority(question.getPriority())
                .attributes(question.getAttributes())
                .questionId(question.getQuestionId())
                .createdDate(question.getCreatedDate())
                .updatedDate(question.getUpdatedDate())
                .updatedAccount(question.getUpdatedAccount())
                .complexityLevel(question.getComplexityLevel())
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
                .type(questionDto.getType())
                .topic(questionDto.getTopic())
                .tipId(questionDto.getTipId())
                .isActive(questionDto.isActive())
                .answers(questionDto.getAnswers())
                .account(questionDto.getAccount())
                .content(questionDto.getContent())
                .category(questionDto.getCategory())
                .priority(questionDto.getPriority())
                .attributes(questionDto.getAttributes())
                .questionId(questionDto.getQuestionId())
                .createdDate(questionDto.getCreatedDate())
                .updatedDate(questionDto.getUpdatedDate())
                .updatedAccount(questionDto.getUpdatedAccount())
                .complexityLevel(questionDto.getComplexityLevel())
                .build();
    }
}
