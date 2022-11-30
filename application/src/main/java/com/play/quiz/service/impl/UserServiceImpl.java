package com.play.quiz.service.impl;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.exception.DuplicateUserException;
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
    public Long save(final AccountDto accountDto) {
        Optional<Account> existingAccount = userRepository.findUserByEmail(accountDto.getEmail());
        if (existingAccount.isPresent()) {
            throw new DuplicateUserException("User already registered in the system");
        }

        Account account = accountMapper.toEntity(accountDto);
        return userRepository.save(account);
    }

    @Override
    public Account findByEmail(final String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NoSuchUserException("User not found"));
    }
}
