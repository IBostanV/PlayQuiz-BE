package com.play.quiz.exception;

public class TokenGenerationException extends RuntimeException {
    public TokenGenerationException() {
        super();
    }

    public TokenGenerationException(String message) {
        super(message);
    }

    public TokenGenerationException(Throwable cause) {
        super(cause);
    }
}
