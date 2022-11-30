package com.play.quiz.exception;

import org.springframework.http.HttpStatus;

public class DuplicateUserException extends RestException{
    public DuplicateUserException(final String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
