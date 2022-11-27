package com.play.quiz.repository;

import com.play.quiz.model.Account;

import java.util.Optional;

public interface UserRepository {

    Long save(final Account accountInfo);

    Optional<Account> findUserByEmail(final String email);
}
