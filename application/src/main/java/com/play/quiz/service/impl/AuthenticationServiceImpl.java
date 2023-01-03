package com.play.quiz.service.impl;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.dto.RoleDto;
import com.play.quiz.enums.UserRole;
import com.play.quiz.mapper.AccountMapper;
import com.play.quiz.model.Account;
import com.play.quiz.model.helpers.AccountInfo;
import com.play.quiz.security.JwtProvider;
import com.play.quiz.service.AuthenticationService;
import com.play.quiz.service.UserService;
import com.play.quiz.util.SystemAssert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final AccountMapper accountMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public AccountInfo register(final AccountDto accountDto) {
        log.info("Register user with email: "+ accountDto.getEmail());
        SystemAssert.isAccountUnique(userService.userExists(accountDto), accountDto.getEmail());
        Account account = userService.save(assignRoles(accountDto));
        userService.sendAccountVerificationEmail(account);

        return buildAccountInfo(account, authenticate(accountDto));
    }

    private AccountDto assignRoles(final AccountDto accountDto) {
        Long roleId = UserRole.ROLE_USER.getRoleId();
        RoleDto roleDto = RoleDto.builder().name(UserRole.ROLE_USER).roleId(roleId).build();
        return accountDto.toBuilder().roles(Collections.singletonList(roleDto)).build();
    }

    @Override
    public AccountInfo login(final AccountDto accountDto) {
        log.info("Login user with email: "+ accountDto.getEmail());
        Account account = userService.findByEmail(accountDto.getEmail());
        SystemAssert.isAccountEnabled(account.isEnabled(), account.getEmail());
        Authentication authentication = authenticate(accountDto);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return buildAccountInfo(account, authentication);
    }

    private AccountInfo buildAccountInfo(final Account account, final Authentication authentication) {
        return new AccountInfo(jwtProvider.generate(authentication), accountMapper.toDto(account));
    }

    private Authentication authenticate(final AccountDto accountDto) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                accountDto.getEmail(), accountDto.getPassword()));
    }
}
