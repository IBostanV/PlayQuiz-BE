package com.play.quiz.security;

import com.play.quiz.domain.Account;
import com.play.quiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    private final UserService userService;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getPrincipal() {
        Authentication authentication = getAuthentication();
        return (User)authentication.getPrincipal();
    }

    @Override
    public Account getAccount() {
        return userService.findByEmail(getPrincipal().getUsername());
    }
}
