package com.play.quiz.service.impl;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.exception.DuplicateUserException;
import com.play.quiz.model.helpers.AccountInfo;
import com.play.quiz.security.JwtProvider;
import com.play.quiz.security.Token;
import com.play.quiz.service.AuthenticationService;
import com.play.quiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AccountInfo register(final AccountDto accountDto) {
        if (userService.userExists(accountDto)) {
            throw new DuplicateUserException("User already registered in the system");
        }

        final AccountDto persistedAccount = userService.save(accountDto);
        return AccountInfo.builder()
                .account(persistedAccount)
                .token(createToken(accountDto))
                .build();
    }

    @Override
    public AccountInfo login(final AccountDto accountDto) {
        final AccountDto existingAccount = userService.findByEmail(accountDto.getEmail());
        return AccountInfo.builder()
                .account(existingAccount)
                .token(createToken(accountDto))
                .build();
    }

    private Token createToken(final AccountDto accountDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountDto.getEmail(), accountDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generate(authentication);
    }
}
