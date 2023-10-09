package com.play.quiz.repository;

import java.util.List;
import java.util.Optional;

import com.play.quiz.domain.Account;

public interface UserRepository {

    Account save(final Account account);

    Optional<Account> findUserByEmail(final String email);

    List<Account> findAll();

    void enableAccount(final Long accountId);

    int updateUserPassword(String userEmail, char[] password);
}
