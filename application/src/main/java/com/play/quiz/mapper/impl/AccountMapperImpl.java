package com.play.quiz.mapper.impl;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.mapper.AccountMapper;
import com.play.quiz.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {
    private final PasswordEncoder passwordEncoder;

    public AccountDto toDto(final Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .email(account.getEmail())
                .name(account.getName())
                .build();
    }

    public Account toEntity(final AccountDto accountDto) {
        return Account.builder()
                .id(accountDto.getId())
                .email(accountDto.getEmail())
                .name(accountDto.getName())
                .password(passwordEncoder.encode(accountDto.getPassword()))
                .build();
    }
}
