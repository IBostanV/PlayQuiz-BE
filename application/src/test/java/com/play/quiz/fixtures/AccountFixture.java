package com.play.quiz.fixtures;

import com.play.quiz.enums.UserRole;
import com.play.quiz.domain.Account;
import com.play.quiz.domain.Role;

import java.util.Collections;
import java.util.List;

public class AccountFixture {

    public static Account getNoRolesAccount() {
        return defaultBuilder(1L)
                .roles(Collections.emptyList())
                .isEnabled(true)
                .build();
    }
    public static Account getAdminAccount() {
        return defaultBuilder(1L)
                .isEnabled(true)
                .build();
    }

    public static Account getDisabledAdminAccount() {
        return defaultBuilder(1L)
                .isEnabled(false)
                .build();
    }

    private static Account.AccountBuilder defaultBuilder(final Long accountId) {
        return Account.builder()
                .accountId(accountId)
                .email("vanyok93@yahoo.com")
                .password("{noop}password".toCharArray())
                .roles(List.of(getUserRole(), getAdminRole()));
    }

    private static Role getUserRole() {
        return new Role(1L, UserRole.ROLE_USER);
    }

    private static Role getAdminRole() {
        return new Role(1L, UserRole.ROLE_ADMIN);
    }
}
