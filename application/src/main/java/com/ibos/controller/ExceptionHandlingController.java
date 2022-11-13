package com.ibos.controller;

import com.ibos.exception.NoSuchUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<Void> userNotFound(final NoSuchUserException userNotFound) {
        log.info(userNotFound.getMessage());
        return ResponseEntity
                .status(userNotFound.getCode())
                .build();
    }
}
