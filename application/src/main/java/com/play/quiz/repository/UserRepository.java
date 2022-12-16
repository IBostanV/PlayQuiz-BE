package com.play.quiz.repository;

import com.play.quiz.model.Account;

import java.util.Optional;

public interface UserRepository {

    Account save(final Account account);

    Optional<Account> findUserByEmail(final String email);
}
