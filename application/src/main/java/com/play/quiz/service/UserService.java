package com.play.quiz.service;

import com.play.quiz.dto.AccountDto;

public interface UserService {

    AccountDto save(final AccountDto accountDto);

    AccountDto findByEmail(final String email);

    void sendAccountVerificationEmail(final AccountDto accountDto);

    boolean userExists(final AccountDto accountDto);

    void activateAccount(final String verificationToken);
}
