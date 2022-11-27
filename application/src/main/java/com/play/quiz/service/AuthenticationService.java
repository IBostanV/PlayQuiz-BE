package com.play.quiz.service;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.security.Token;

public interface AuthenticationService {

    Token login(final AccountDto accountDto);
}
