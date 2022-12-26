package com.play.quiz.mapper;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {

    private @Autowired PasswordEncoder passwordEncoder;

    @Mapping(target = "password", ignore = true)
    public abstract AccountDto toDto(final Account account);

    @Mapping(target = "password", source = "password", qualifiedByName = "handlePassword")
    public abstract Account toEntity(final AccountDto accountDto);

    @Named("handlePassword")
    protected String handlePassword(final String password) {
        if (Objects.isNull(password)) return null;

        return passwordEncoder.encode(password);
    }
}
