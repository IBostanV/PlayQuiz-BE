package com.play.quiz.service;

import com.play.quiz.model.Account;
import com.play.quiz.model.VerificationToken;

import java.util.Optional;

public interface VerificationTokenService {

    VerificationToken createVerificationToken(final Account account);

    Optional<VerificationToken> findByToken(final String token);

    VerificationToken save(final VerificationToken verificationToken);
}
