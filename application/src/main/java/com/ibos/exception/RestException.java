package com.ibos.exception;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {

    private final HttpStatus httpCode;
    private static final HttpStatus DEFAULT_ERROR_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public RestException(final String message) {
        this(message, DEFAULT_ERROR_STATUS);
    }

    public RestException(final String message, HttpStatus httpCode) {
        super(message);
        this.httpCode = httpCode;
    }

    public RestException(final Throwable cause) {
        super(cause);
        this.httpCode = DEFAULT_ERROR_STATUS;
    }

    public RestException(final String message, final Throwable cause) {
        super(message, cause);
        this.httpCode = DEFAULT_ERROR_STATUS;
    }

    public HttpStatus getCode() {
        return httpCode;
    }
}
