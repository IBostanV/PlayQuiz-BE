package com.play.quiz.service.impl;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.mapper.AccountMapper;
import com.play.quiz.model.Account;
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
    private final AccountMapper accountMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public AccountInfo register(final AccountDto accountDto) {
        SystemAssert.isAccountUnique(userService.userExists(accountDto), accountDto.getEmail());
        Account account = userService.save(accountDto);
        userService.sendAccountVerificationEmail(account);

        return buildAccountInfo(account, authenticate(accountDto));
    }

    @Override
    public AccountInfo login(final AccountDto accountDto) {
        Account account = userService.findByEmail(accountDto.getEmail());
        SystemAssert.isAccountEnabled(account.isEnabled(), account.getEmail());
        Authentication authentication = authenticate(accountDto);

        return buildAccountInfo(account, authentication);
    }

    private AccountInfo buildAccountInfo(final Account account, final Authentication authentication) {
        AccountDto accountDto = accountMapper.toDto(account);
        return new AccountInfo(jwtProvider.generate(authentication), accountDto);
    }

    private Authentication authenticate(final AccountDto accountDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountDto.getEmail(), accountDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
