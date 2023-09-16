package com.play.quiz.service;

import com.play.quiz.domain.Account;
import com.play.quiz.domain.VerificationToken;

import java.util.Optional;

public interface VerificationTokenService {

    VerificationToken createVerificationToken(final Account account);

    Optional<VerificationToken> findByToken(String token);

    VerificationToken save(final VerificationToken verificationToken);
}
