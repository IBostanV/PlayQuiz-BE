package com.play.quiz.mapper;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.model.Account;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface AccountMapper {

    AccountDto toDto(final Account account);

    Account toEntity(final AccountDto accountDto);

    Account fromResultSet(final ResultSet resultSet) throws SQLException;
}
