package com.play.quiz.controller;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RestEndpoint.CONTEXT_PATH + "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountDto>> getAccountList() {
        return ResponseEntity.ok(userService.getAccountList());
    }
}
