package com.play.quiz.mapper;

import java.util.List;

import com.play.quiz.dto.AnswerDto;
import com.play.quiz.dto.QuestionDto;
import com.play.quiz.dto.QuizDto;
import com.play.quiz.dto.translation.QuestionTranslationDto;
import com.play.quiz.model.Answer;
import com.play.quiz.model.Question;
import com.play.quiz.model.Quiz;
import com.play.quiz.model.translation.QuestionTranslation;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface QuizMapper {

    Quiz toEntity(final QuizDto quizDto);

    @IterableMapping(qualifiedByName = "handlingQuestions")
    List<QuestionDto> toQuestionList(final List<Question> questionList);

    @Named("handlingQuestions")
    @Mapping(source = "questionId", target = "id")
    QuestionDto withQuestions(final Question question);

    @Mapping(target = "question", ignore = true)
    QuestionTranslationDto withQuestionTranslationDto(final QuestionTranslation questionTranslation);

    @Mapping(target = "quizType", ignore = true)
    QuizDto toDto(final Quiz quiz);

    @IterableMapping(qualifiedByName = "handlingAnswers")
    List<AnswerDto> toAnswerDtoList(final List<Answer> asnwerList);

    @Named("handlingAnswers")
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "answerTranslations", ignore = true)
    AnswerDto toAnswerDto(final Answer answer);
}
