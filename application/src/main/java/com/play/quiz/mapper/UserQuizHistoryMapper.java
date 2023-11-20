package com.play.quiz.mapper;

import java.util.List;

import com.play.quiz.domain.Answer;
import com.play.quiz.domain.Question;
import com.play.quiz.domain.UserQuizHistory;
import com.play.quiz.dto.AnswerDto;
import com.play.quiz.dto.QuestionDto;
import com.play.quiz.dto.UserQuizHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserQuizHistoryMapper {

    @Mapping(target = "trophies", ignore = true)
    UserQuizHistoryDto toDto(final UserQuizHistory userQuizHistory);

    UserQuizHistory toEntity(final UserQuizHistoryDto userQuizHistoryDto);

    default AnswerDto answerToAnswerDto(final Answer answer) {
        return AnswerDto.builder()
                .content(answer.getContent())
                .termId(answer.getGlossary().getTermId())
                .glossaryAttachment(answer.getGlossary().getAttachment())
                .build();
    }

    default List<AnswerDto> answersToAnswersDto(final List<Answer> answerList) {
        return answerList.stream()
                .map(this::answerToAnswerDto)
                .toList();
    }

    default QuestionDto questionToQuestionDto(final Question question) {
        return QuestionDto.builder()
                .type(question.getType())
                .id(question.getQuestionId())
                .content(question.getContent())
                .answers(answersToAnswersDto(question.getAnswers()))
                .build();
    }
}
