package com.play.quiz.exception;

public class AccountDisabledException extends RuntimeException{
    public AccountDisabledException() {
        super();
    }

    public AccountDisabledException(String message) {
        super(message);
    }
}
