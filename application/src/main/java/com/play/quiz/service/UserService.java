package com.play.quiz.service;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.model.Account;

public interface UserService {

    Long save(final AccountDto userInfo);
    Account findByEmail(final String email);
}
