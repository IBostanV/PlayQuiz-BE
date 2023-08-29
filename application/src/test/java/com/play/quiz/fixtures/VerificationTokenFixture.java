package com.play.quiz.fixtures;

import com.play.quiz.domain.VerificationToken;

import java.util.UUID;

public class VerificationTokenFixture {
    public static VerificationToken getVerificationToken() {
        return VerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .account(AccountFixture.getAdminAccount())
                .build();
    }
}
