package com.play.quiz.mapper.impl;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.mapper.AccountMapper;
import com.play.quiz.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {
    private final PasswordEncoder passwordEncoder;

    public AccountDto toDto(final Account account) {
        return AccountDto.builder()
                .name(account.getName())
                .email(account.getEmail())
                .id(account.getAccountId())
                .isEnabled(account.isEnabled())
                .birthday(account.getBirthday())
                .build();
    }

    public AccountDto toDtoWithId(final Account account, final Long id) {
        return AccountDto.builder()
                .id(id)
                .name(account.getName())
                .email(account.getEmail())
                .isEnabled(account.isEnabled())
                .birthday(account.getBirthday())
                .build();
    }

    public Account toEntity(final AccountDto accountDto) {
        return Account.builder()
                .name(accountDto.getName())
                .email(accountDto.getEmail())
                .accountId(accountDto.getId())
                .isEnabled(accountDto.isEnabled())
                .birthday(accountDto.getBirthday())
                .password(handlePassword(accountDto))
                .build();
    }

    private String handlePassword(final AccountDto accountDto) {
        if (Objects.isNull(accountDto.getPassword())) return null;

        return passwordEncoder.encode(accountDto.getPassword());
    }
}
