package com.play.quiz.controller;

import com.play.quiz.dto.QuizDto;
import com.play.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestEndpoint.CONTEXT_PATH + "/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizDto> createQuiz(@RequestBody final QuizDto quizDto) {
        return ResponseEntity.ok(quizService.create(quizDto));
    }

    @GetMapping(value = "/{quizId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizDto> getQuiz(@PathVariable final Long quizId) {
        return ResponseEntity.ok(quizService.getById(quizId));
    }
}
