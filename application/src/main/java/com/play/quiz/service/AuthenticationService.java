package com.play.quiz.service;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.model.helpers.AccountInfo;

public interface AuthenticationService {

    AccountInfo login(final AccountDto accountDto);

    AccountInfo register(final AccountDto accountDto);
}
