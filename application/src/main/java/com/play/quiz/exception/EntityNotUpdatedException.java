package com.play.quiz.exception;

public class EntityNotUpdatedException extends RuntimeException{
    public EntityNotUpdatedException(String message) {
        super(message);
    }

    public EntityNotUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
