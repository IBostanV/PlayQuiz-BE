package com.play.quiz.fixtures;

import com.play.quiz.model.helpers.AccountInfo;
import com.play.quiz.security.Token;

import java.util.UUID;

public class AccountInfoFixture {
    public static AccountInfo getAccountInfo() {
        return defaultBuilder()
                .account(AccountDtoFixture.getAccountDto())
                .build();
    }

    public static AccountInfo getAccountInfoWithDisabledAccount() {
        return defaultBuilder()
                .account(AccountDtoFixture.getDisabledAccountDto())
                .build();
    }

    public static AccountInfo getAccountInfoWithNoEmailAccount() {
        return defaultBuilder()
                .account(AccountDtoFixture.getAccountDtoWithNoEmail())
                .build();
    }

    private static AccountInfo.AccountInfoBuilder defaultBuilder() {
        return AccountInfo.builder()
                .token(new Token(UUID.randomUUID().toString()));
    }
}
