package com.play.quiz.fixtures;

import com.play.quiz.dto.AccountDto;

import java.time.LocalDate;

public class AccountDtoFixture {

    private static final String DEFAULT_EMAIL = "email@gmail.com";

    public static AccountDto getAccountDto() {
        return defaultBuilder(1L)
                .isEnabled(true)
                .email(DEFAULT_EMAIL)
                .build();
    }

    public static AccountDto getDisabledAccountDto() {
        return defaultBuilder(1L)
                .isEnabled(false)
                .email(DEFAULT_EMAIL)
                .build();
    }

    public static AccountDto getAccountDtoWithNoEmail() {
        return defaultBuilder(1L)
                .isEnabled(true)
                .build();
    }

    private static AccountDto.AccountDtoBuilder defaultBuilder(final Long userId) {
        return AccountDto.builder()
                .id(userId)
                .password("password")
                .name("User")
                .birthday(LocalDate.of(2022, 12, 31));
    }
}
