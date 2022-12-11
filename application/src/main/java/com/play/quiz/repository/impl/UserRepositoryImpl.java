package com.play.quiz.repository.impl;

import com.play.quiz.model.Account;
import com.play.quiz.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

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
        final Long nextUserId = jdbcTemplate.queryForObject(userSequenceNextVal, Long.class);
        jdbcTemplate.update(saveUserSql, nextUserId, account.getEmail(), account.getPassword());

        return ((Account)account.clone()).withId(nextUserId);
    }

    @Override
    public Optional<Account> findUserByEmail(final String email) {
        return namedParameterJdbcTemplate.query(findUserByEmailSql, Collections.singletonMap("email", email),
                resultSet -> resultSet.next()
                        ? Optional.of(mapResultSet(resultSet))
                        : Optional.empty());
    }

    private Account mapResultSet(final ResultSet resultSet) throws SQLException {
        return Account.builder()
                .accountId(resultSet.getObject(1, Long.class))
                .email(resultSet.getObject(2, String.class))
                .password(resultSet.getObject(3, String.class))
                .build();
    }
}
