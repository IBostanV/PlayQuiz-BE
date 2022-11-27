package com.play.quiz.service.impl;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.exception.NoSuchUserException;
import com.play.quiz.mapper.impl.AccountMapperImpl;
import com.play.quiz.model.Account;
import com.play.quiz.repository.UserRepository;
import com.play.quiz.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final AccountMapperImpl userMapper;
    private final UserRepository userRepository;

    @Override
    public Long save(final AccountDto accountDto) {
        Account account = userMapper.toEntity(accountDto);
        return userRepository.save(account);
    }

    @Override
    public Account findByEmail(final String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NoSuchUserException("User not found"));
    }
}
