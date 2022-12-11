package com.play.quiz.exception;

public class AccountDisabledException extends RuntimeException{
    public AccountDisabledException() {
        super();
    }

    public AccountDisabledException(final String message) {
        super(message);
    }
}
