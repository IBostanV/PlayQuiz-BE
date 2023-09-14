package com.play.quiz.controller;

import static com.play.quiz.controller.RestEndpoint.REQUEST_MAPPING_USER_HISTORY;

import com.play.quiz.dto.UserQuizHistoryDto;
import com.play.quiz.service.UserQuizHistoryService;
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
@RequestMapping(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_USER_HISTORY)
@RequiredArgsConstructor
public class UserQuizHistoryController {

    private final UserQuizHistoryService userQuizHistoryService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserQuizHistoryDto> saveUserQuiz(@RequestBody final UserQuizHistoryDto userQuizHistoryDto) {
        return ResponseEntity.ok(userQuizHistoryService.save(userQuizHistoryDto));
    }

    @GetMapping(value = "/history/{historyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserQuizHistoryDto> getUserQuiz(@PathVariable final Long historyId) {
        return ResponseEntity.ok(userQuizHistoryService.getById(historyId));
    }
}
