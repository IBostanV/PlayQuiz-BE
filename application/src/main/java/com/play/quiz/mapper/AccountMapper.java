package com.play.quiz.mapper;

import java.util.List;
import java.util.Objects;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.domain.Account;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {

    private @Autowired PasswordEncoder passwordEncoder;

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", source = "accountId")
    public abstract AccountDto toDto(final Account account);

    @IterableMapping(qualifiedByName = "mappingFields")
    public abstract List<AccountDto> toDtoList(final List<Account> account);

    @Mapping(target = "accountId", source = "id")
    @Mapping(target = "password", source = "password", qualifiedByName = "handlePassword")
    public abstract Account toEntity(final AccountDto accountDto);

    @Named("handlePassword")
    protected char[] handlePassword(final char[] password) {
        return Objects.nonNull(password) ? passwordEncoder.encode(new String(password)).toCharArray() : null;
    }

    @Named("mappingFields")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", source = "accountId")
    @Mapping(target = "isEnabled", source = "enabled")
    protected abstract AccountDto withoutAnswers(final Account account);
}
