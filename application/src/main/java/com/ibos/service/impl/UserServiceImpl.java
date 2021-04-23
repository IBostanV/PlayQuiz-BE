package com.ibos.service.impl;

import com.ibos.dto.UserDto;
import com.ibos.exception.NoSuchUserException;
import com.ibos.mapper.UserMapper;
import com.ibos.model.User;
import com.ibos.repository.UserRepository;
import com.ibos.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public void login(final UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        Optional<User> existingUser = userRepository.findUserByEmail(user);
        if (existingUser.isEmpty()) {
            throw new NoSuchUserException("User not found");
        }
    }

    @Override
    public Long save(final UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return userRepository.save(user);
    }
}
