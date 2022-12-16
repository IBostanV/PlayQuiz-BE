package com.play.quiz.repository.impl;

import com.play.quiz.model.Account;
import com.play.quiz.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final String saveUserSql;
    private final String findUserByEmailSql;
    private final JdbcTemplate jdbcTemplate;
    private final String userSequenceNextVal;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Account save(final Account account) {
        return Objects.nonNull(account.getAccountId()) ? handleUpdate(account) : handleInsert(account);
    }

    private Account handleInsert(final Account account) {
        final Long nextUserId = jdbcTemplate.queryForObject(userSequenceNextVal, Long.class);
        return executeSave(nextUserId, account);
    }

    private Account handleUpdate(final Account account) {
        return executeSave(account.getAccountId(), account);
    }

    private Account executeSave(final Long accountId, final Account account) {
        namedParameterJdbcTemplate.update(saveUserSql, getProperties(accountId, account));
        return ((Account) account.clone()).withId(accountId);
    }

    private Map<String, Object> getProperties(final Long accountId, final Account account) {
        final Function<Object, Object> fillNull = (input) -> Objects.nonNull(input) ? input : "";

        return Map.of(
                "accountId", accountId,
                "email", account.getEmail(),
                "isEnabled", account.isEnabled(),
                "password", account.getPassword(),
                "name", fillNull.apply(account.getName()),
                "birthday", fillNull.apply(account.getBirthday()));
    }

    @Override
    public Optional<Account> findUserByEmail(final String email) {
        return namedParameterJdbcTemplate.query(findUserByEmailSql, Collections.singletonMap("email", email),
                resultSet -> resultSet.next() ? Optional.of(mapResultSet(resultSet)) : Optional.empty());
    }

    private Account mapResultSet(final ResultSet resultSet) throws SQLException {
        return Account.builder()
                .accountId(resultSet.getObject(1, Long.class))
                .email(resultSet.getObject(2, String.class))
                .password(resultSet.getObject(3, String.class))
                .isEnabled(resultSet.getObject(4, Boolean.class))
                .birthday(resultSet.getObject(5, LocalDate.class))
                .name(resultSet.getObject(6, String.class))
                .build();
    }
}
