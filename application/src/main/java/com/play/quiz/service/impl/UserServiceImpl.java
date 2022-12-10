package com.play.quiz.service.impl;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.exception.NoSuchUserException;
import com.play.quiz.mapper.impl.AccountMapperImpl;
import com.play.quiz.model.Account;
import com.play.quiz.repository.UserRepository;
import com.play.quiz.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AccountMapperImpl accountMapper;

    @Override
    public AccountDto save(final AccountDto accountDto) {
        final Account account = accountMapper.toEntity(accountDto);
        final Long userId = userRepository.save(account);
        return accountMapper.toDtoWithId(account, userId);
    }

    @Override
    public AccountDto findByEmail(final String email) {
        final Account account = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NoSuchUserException("User not found"));
        return accountMapper.toDto(account);
    }

    @Override
    public boolean userExists(final AccountDto accountDto) {
        Optional<Account> existingUser = userRepository.findUserByEmail(accountDto.getEmail());
        return existingUser.isPresent();
    }
}
