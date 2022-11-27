package com.play.quiz.controller;

import com.play.quiz.dto.QuestionDto;
import com.play.quiz.enums.QuestionCategory;
import com.play.quiz.service.QuizQuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RestEndpoint.CONTEXT_PATH + "/quiz")
@AllArgsConstructor
public class QuizQuestionController {
    private QuizQuestionService quizQuestionService;

    @GetMapping(value = "/get-all-questions")
    public ResponseEntity<List<QuestionDto>> getQuestions() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(quizQuestionService.findAll());
    }

    @GetMapping(value = "/get-category-questions")
    public ResponseEntity<List<QuestionDto>> getCategoryQuestions(@RequestParam QuestionCategory category) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(quizQuestionService.getCategoryQuestions(category));
    }
}