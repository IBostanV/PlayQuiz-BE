package com.play.quiz.exception;

public class UserUpdateException extends RuntimeException {

    public UserUpdateException() {
        super();
    }

    public UserUpdateException(String message) {
        super(message);
    }
}
