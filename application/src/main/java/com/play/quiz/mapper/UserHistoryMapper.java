package com.play.quiz.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.play.quiz.domain.Answer;
import com.play.quiz.domain.Glossary;
import com.play.quiz.domain.Question;
import com.play.quiz.domain.UserHistory;
import com.play.quiz.dto.AnswerDto;
import com.play.quiz.dto.GlossaryDto;
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
                .glossary(glossaryToGlossaryDto(answer.getGlossary()))
                .build();
    }

    default GlossaryDto glossaryToGlossaryDto(final Glossary glossary) {
        return GlossaryDto.builder()
                .type(glossary.getType())
                .key(glossary.getKey())
                .value(glossary.getValue())
                .build();
    }

    default List<AnswerDto> answersToAnswersDto(final List<Answer> answerList) {
        return answerList.stream()
                .map(this::answerToAnswerDto)
                .collect(Collectors.toList());
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
