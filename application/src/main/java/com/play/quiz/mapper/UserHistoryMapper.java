package com.play.quiz.mapper;

import com.play.quiz.domain.Answer;
import com.play.quiz.domain.Question;
import com.play.quiz.domain.UserHistory;
import com.play.quiz.dto.AnswerDto;
import com.play.quiz.dto.QuestionDto;
import com.play.quiz.dto.UserHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserHistoryMapper {

    @Mapping(target = "trophies", ignore = true)
    UserHistoryDto toDto(final UserHistory userHistory);

    UserHistory toEntity(final UserHistoryDto userHistoryDto);

    default AnswerDto answerToAnswerDto(final Answer answer) {
        return AnswerDto.builder()
                .content(answer.getContent())
                .build();
    }

    default QuestionDto questionToQuestionDto(final Question question) {
        return QuestionDto.builder()
                .content(question.getContent())
                .build();
    }
}
