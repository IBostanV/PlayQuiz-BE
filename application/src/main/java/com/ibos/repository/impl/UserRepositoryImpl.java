package com.ibos.repository.impl;

import com.ibos.model.User;
import com.ibos.repository.UserRepository;
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
    private final String findUserByEmailSql;
    private final JdbcTemplate jdbcTemplate;
    private final String userSequenceNextVal;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Long save(final User userInfo) {
        final Long nextUserId = jdbcTemplate.queryForObject(userSequenceNextVal, Long.class);
        jdbcTemplate.update(saveUserSql, nextUserId, userInfo.getEmail(), userInfo.getPassword());
        return nextUserId;
    }

    @Override
    public Optional<User> findUserByEmail(final User userInfo) {
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(findUserByEmailSql, Collections.singletonMap("email", userInfo.getEmail()), User.class));
    }
}
