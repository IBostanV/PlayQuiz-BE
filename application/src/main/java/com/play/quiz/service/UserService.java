package com.play.quiz.service;

import com.play.quiz.dto.AccountDto;

public interface UserService {

    AccountDto save(final AccountDto accountDto);

    AccountDto findByEmail(final String email);

    boolean userExists(final AccountDto accountDto);
}
