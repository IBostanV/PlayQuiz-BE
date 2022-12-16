package com.play.quiz.service.impl;

import com.play.quiz.model.Account;
import com.play.quiz.model.VerificationToken;
import com.play.quiz.repository.VerificationTokenRepository;
import com.play.quiz.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    @Value("#{${application.email.token.validity.period}}:1440")
    private Integer verificationTokenValidityPeriod;

    @Override
    public VerificationToken createVerificationToken(final Account account) {
        final VerificationToken verificationToken = buildVerificationToken(account);
        return verificationTokenRepository.save(verificationToken);
    }

    @Override
    public Optional<VerificationToken> findByToken(final String token) {
        final VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        return Optional.ofNullable(verificationToken);
    }

    @Override
    public VerificationToken save(final VerificationToken verificationToken) {
        return verificationTokenRepository.save(verificationToken);
    }

    private VerificationToken buildVerificationToken(final Account account) {
        return VerificationToken.builder()
                .account(account)
                .issuedDate(LocalDateTime.now())
                .token(UUID.randomUUID().toString())
                .validityPeriod(verificationTokenValidityPeriod)
                .build();
    }
}
