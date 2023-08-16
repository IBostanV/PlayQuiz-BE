package com.play.quiz.mapper;

import java.time.LocalDateTime;
import java.util.List;

import com.play.quiz.dto.AnswerDto;
import com.play.quiz.dto.QuestionDto;
import com.play.quiz.dto.translation.QuestionTranslationDto;
import com.play.quiz.enums.QuestionType;
import com.play.quiz.model.Account;
import com.play.quiz.model.Answer;
import com.play.quiz.model.Question;
import com.play.quiz.model.translation.QuestionTranslation;
import com.play.quiz.security.facade.AuthenticationFacade;
import com.play.quiz.service.UserService;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Mapper(componentModel = "spring",
        imports = LocalDateTime.class)
public abstract class QuestionMapper {

    @Autowired private UserService userService;
    @Autowired private AuthenticationFacade authenticationFacade;

    @Mapping(target = "id", source = "questionId")
    public abstract QuestionDto mapToDto(final Question question);

    public List<QuestionDto> mapToDtoList(final List<Question> questionList) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            return mapToDtoListInternal(questionList);
        }
        return mapDtoNoAnswerList(questionList);
    }

    protected abstract List<QuestionDto> mapToDtoListInternal(final List<Question> questionList);

    @IterableMapping(qualifiedByName = "withoutAnswers")
    public abstract List<QuestionDto> mapDtoNoAnswerList(final List<Question> questionList);

    @Mapping(target = "questionId", source = "id")
    @Mapping(target = "account", expression = "java(getAccount())")
    @Mapping(target = "createdDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "type", expression = "java(getQuestionType(questionDto.getContent()))")
    public abstract Question mapToEntity(final QuestionDto questionDto);

    @Named("withoutAnswers")
    @Mapping(target = "id", source = "questionId")
    @Mapping(target = "answers", ignore = true)
    protected abstract QuestionDto withoutAnswers(Question question);

    @Mapping(target = "question", ignore = true)
    protected abstract QuestionTranslationDto mapTranslationToDto(QuestionTranslation source);

    @Mapping(target = "question", ignore = true)
    protected abstract AnswerDto mapAnswersToDto(Answer source);

    protected Account getAccount() {
        String emailAsUsername = authenticationFacade.getPrincipal().getUsername();
        return userService.findByEmail(emailAsUsername);
    }

    protected QuestionType getQuestionType(final String content) {
        if (content.contains("%s")) {
            return QuestionType.TEMPLATE;
        }
        return QuestionType.CREATED;
    }

    public abstract List<Question> mapToEntityList(final List<QuestionDto> quizQuestions);
}
