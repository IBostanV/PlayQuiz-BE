package com.play.quiz.service.impl;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.model.helpers.AccountInfo;
import com.play.quiz.security.JwtProvider;
import com.play.quiz.service.AuthenticationService;
import com.play.quiz.service.UserService;
import com.play.quiz.util.SystemAssert;
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
        SystemAssert.isAccountUnique(userService.userExists(accountDto), accountDto.getEmail());

        final AccountDto persistedAccount = userService.save(accountDto);
        final Authentication authentication = authenticateUser(accountDto);

        userService.sendAccountVerificationEmail(persistedAccount);

        return buildAccountInfo(persistedAccount, authentication);
    }

    @Override
    public AccountInfo login(final AccountDto accountDto) {
        final AccountDto existingAccount = userService.findByEmail(accountDto.getEmail());
        SystemAssert.isAccountEnabled(accountDto.isEnabled(), accountDto.getEmail());
        final Authentication authentication = authenticateUser(accountDto);

        return buildAccountInfo(existingAccount, authentication);
    }

    private AccountInfo buildAccountInfo(final AccountDto accountDto, final Authentication authentication) {
        return new AccountInfo(jwtProvider.generate(authentication), accountDto);
    }

    private Authentication authenticateUser(final AccountDto accountDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountDto.getEmail(), accountDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
