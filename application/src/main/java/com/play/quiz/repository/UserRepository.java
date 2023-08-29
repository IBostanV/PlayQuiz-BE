package com.play.quiz.repository;

import com.play.quiz.domain.Account;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Account save(final Account account);

    Optional<Account> findUserByEmail(final String email);

    List<Account> findAll();

    void enableAccount(final Long accountId);
}
