package com.play.quiz.mapper.impl;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.mapper.AccountMapper;
import com.play.quiz.model.Account;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AccountMapperImpl implements AccountMapper {
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
                .password(accountDto.getPassword())
                .build();
    }

    public Account fromResultSet(final ResultSet resultSet) throws SQLException {
        return Account.builder()
                .id(resultSet.getObject(1, Integer.class))
                .email(resultSet.getObject(2, String.class))
                .password(resultSet.getObject(3, String.class))
                .build();
    }
}
