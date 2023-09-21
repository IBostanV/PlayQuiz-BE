package com.play.quiz.exception;

public class EmailSendFailedException extends RuntimeException{
    public EmailSendFailedException(String message) {
        super(message);
    }
}
