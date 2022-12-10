package com.play.quiz.controller;

import com.play.quiz.exception.NoSuchUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<List<Pair<String, String>>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        final List<Pair<String, String>> violatedFields = fieldErrors.stream()
                .filter(fieldError -> Objects.nonNull(fieldError.getDefaultMessage()))
                .map(fieldError -> Pair.of(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(violatedFields);
    }
}
