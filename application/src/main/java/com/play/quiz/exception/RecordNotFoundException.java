package com.play.quiz.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException() {
        super();
    }

    public RecordNotFoundException(final String message) {
        super(message);
    }
}
