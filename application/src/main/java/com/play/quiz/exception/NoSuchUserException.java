package com.play.quiz.exception;

import org.springframework.http.HttpStatus;

public class NoSuchUserException extends RestException {

    public NoSuchUserException(final String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
