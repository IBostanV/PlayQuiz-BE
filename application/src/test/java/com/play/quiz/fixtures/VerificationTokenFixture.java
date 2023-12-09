package com.play.quiz.fixtures;

import java.time.LocalDateTime;

import com.play.quiz.domain.Account;
import com.play.quiz.domain.VerificationToken;

public class VerificationTokenFixture {
    public static VerificationToken getVerificationToken() {
        return VerificationToken.builder()
                .token("verification_token")
                .issuedDate(LocalDateTime.of(2023, 12, 31, 10, 0))
                .account(Account.builder().accountId(2L).build())
                .build();
    }
}
