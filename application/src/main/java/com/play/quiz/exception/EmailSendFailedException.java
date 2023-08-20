package com.play.quiz.exception;

public class EmailSendFailedException extends RuntimeException{
    public EmailSendFailedException(final String message) {
        super(message);
    }
}
