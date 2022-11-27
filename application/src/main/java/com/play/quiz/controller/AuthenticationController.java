package com.play.quiz.controller;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.mapper.impl.AccountMapperImpl;
import com.play.quiz.service.AuthenticationService;
import com.play.quiz.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AccountMapperImpl userMapper;
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> login(@RequestBody final AccountDto user) {
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authenticationService.login(user).getValue())
                .body(userMapper.toDto(userService.findByEmail(user.getEmail())));
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long register(@RequestBody final AccountDto accountDto) {
        return userService.save(accountDto);
    }
}
