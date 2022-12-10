package com.play.quiz.mapper;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.model.Account;

public interface AccountMapper {

    AccountDto toDto(final Account account);

    AccountDto toDtoWithId(final Account account, final Long id);

    Account toEntity(final AccountDto accountDto);
}
