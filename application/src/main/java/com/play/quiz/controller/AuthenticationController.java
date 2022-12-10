package com.play.quiz.controller;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.model.helpers.AccountInfo;
import com.play.quiz.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> login(@Valid @RequestBody final AccountDto accountDto) {
        final AccountInfo accountInfo = authenticationService.login(accountDto);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, accountInfo.getToken().getValue())
                .body(accountInfo.getAccount());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> register(@Valid @RequestBody final AccountDto accountDto) {
        final AccountInfo accountInfo = authenticationService.register(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, accountInfo.getToken().getValue())
                .body(accountInfo.getAccount());
    }
}
