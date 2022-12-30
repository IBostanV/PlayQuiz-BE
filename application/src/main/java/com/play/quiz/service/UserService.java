package com.play.quiz.service;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.model.Account;

import java.util.List;

public interface UserService {

    Account save(final AccountDto accountDto);

    Account findByEmail(final String email);

    List<AccountDto> getAccountList();

    boolean userExists(final AccountDto accountDto);

    void sendAccountVerificationEmail(final Account accountDto);

    void activateAccount(final String verificationToken);
}
