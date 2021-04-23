package com.ibos.repository;

import com.ibos.model.User;

import java.util.Optional;

public interface UserRepository {
    Long save(final User userInfo);

    Optional<User> findUserByEmail(final User userInfo);
}
