package com.play.quiz.controller;

import com.play.quiz.dto.QuestionDto;
import com.play.quiz.enums.CategoryType;
import com.play.quiz.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RestEndpoint.CONTEXT_PATH + "/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> save(@Valid @RequestBody final QuestionDto questionDto) {
        return ResponseEntity.ok(questionService.save(questionDto));
    }

    @DeleteMapping(value = "/{questionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable final Long questionId) {
        questionService.deleteById(questionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDto>> getQuestions() {
        return ResponseEntity.ok(questionService.findAll());
    }

    @GetMapping(value = "/category/{type}")
    public ResponseEntity<List<QuestionDto>> getCategoryQuestions(@PathVariable final CategoryType type) {
        return ResponseEntity.ok(questionService.findByCategoryType(type));
    }

    @PostMapping(value = "/generator/template",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDto>> generateFromTemplate(@Valid @RequestBody final QuestionDto questionDto) {
        return ResponseEntity.ok(questionService.generateFromTemplate(questionDto));
    }
}
