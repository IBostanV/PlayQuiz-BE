package com.play.quiz.util;

import com.play.quiz.dto.QuestionDto;
import com.play.quiz.enums.QuestionType;
import com.play.quiz.exception.AccountDisabledException;
import com.play.quiz.exception.DuplicateUserException;
import com.play.quiz.exception.IllegalTemplateQuestionException;
import org.springframework.util.Assert;

import java.util.Objects;

public abstract class SystemAssert extends Assert {
    public static void isAccountEnabled(boolean isEnabled, String userEmail) {
        if (!isEnabled) {
            throw new AccountDisabledException("Account "+ userEmail +" is disabled");
        }
    }

    public static void isAccountUnique(boolean userExists, String userEmail) {
        if (userExists) {
            throw new DuplicateUserException("User "+ userEmail +" already exists");
        }
    }

    public static void isTemplateQuestion(final QuestionDto questionDto) {
        if (!Objects.equals(QuestionType.TEMPLATE, questionDto.getType())) {
            throw new IllegalTemplateQuestionException("Question ["+ questionDto.getId() +"] is not of type TEMPLATE");
        }
    }
}
