package com.play.quiz.fixtures;

import java.util.UUID;

import com.play.quiz.domain.helpers.AccountInfo;

public class AccountInfoFixture {
    public static AccountInfo getAccountInfo() {
        return defaultBuilder()
                .account(AccountDtoFixture.getEnabledAccountDto())
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
                .jwtToken(UUID.randomUUID().toString());
    }
}
