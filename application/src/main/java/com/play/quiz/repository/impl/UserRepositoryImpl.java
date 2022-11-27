package com.play.quiz.repository.impl;

import com.play.quiz.mapper.impl.AccountMapperImpl;
import com.play.quiz.model.Account;
import com.play.quiz.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final String saveUserSql;
    private final AccountMapperImpl userMapper;
    private final String findUserByEmailSql;
    private final JdbcTemplate jdbcTemplate;
    private final String userSequenceNextVal;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Long save(final Account accountInfo) {
        final Long nextUserId = jdbcTemplate.queryForObject(userSequenceNextVal, Long.class);
        jdbcTemplate.update(saveUserSql, nextUserId, accountInfo.getEmail(), accountInfo.getPassword());
        return nextUserId;
    }

    @Override
    public Optional<Account> findUserByEmail(final String email) {
        return namedParameterJdbcTemplate.query(findUserByEmailSql, Collections.singletonMap("email", email),
                resultSet -> resultSet.next() ? Optional.of(userMapper.fromResultSet(resultSet)) : Optional.empty());
    }
}
