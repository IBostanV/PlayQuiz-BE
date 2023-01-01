package com.play.quiz.controller;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.model.helpers.AccountInfo;
import com.play.quiz.service.AuthenticationService;
import com.play.quiz.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Value("${application.domain.host.url}")
    private String domainHostUrl;

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AccountDto> login(@Valid @RequestBody final AccountDto accountDto) {
        final AccountInfo accountInfo = authenticationService.login(accountDto);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, accountInfo.getToken().getValue())
                .body(accountInfo.getAccount());
    }

    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AccountDto> register(@Valid @RequestBody final AccountDto accountDto) {
        final AccountInfo accountInfo = authenticationService.register(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, accountInfo.getToken().getValue())
                .body(accountInfo.getAccount());
    }

    @GetMapping(value = "/activate-account")
    public ResponseEntity<Void> activateAccount(@RequestParam final String token, final HttpServletResponse response) {
        handleActivation(token, response);
        return ResponseEntity.ok().build();
    }

    private void handleActivation(final String token, final HttpServletResponse response) {
        try {
            userService.activateAccount(token);
            response.sendRedirect(domainHostUrl);
        } catch (IOException exception) {
            log.error(exception.getMessage());
            throw new RuntimeException(exception);
        }
    }
}
