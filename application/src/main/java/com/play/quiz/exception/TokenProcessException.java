package com.play.quiz.exception;

public class TokenProcessException extends RuntimeException {
    public TokenProcessException() {
        super();
    }

    public TokenProcessException(String message) {
        super(message);
    }

    public TokenProcessException(Throwable cause) {
        super(cause);
    }
}
