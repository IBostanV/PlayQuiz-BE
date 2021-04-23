package com.ibos.service;

import com.ibos.dto.UserDto;

public interface UserService {
    void login(final UserDto user);

    Long save(final UserDto userInfo);
}
