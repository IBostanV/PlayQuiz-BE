package com.play.quiz.service;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.model.Account;

public interface UserService {

    Account save(final AccountDto accountDto);

    Account findByEmail(final String email);

    boolean userExists(final AccountDto accountDto);

    void sendAccountVerificationEmail(final Account accountDto);

    void activateAccount(final String verificationToken);
}
