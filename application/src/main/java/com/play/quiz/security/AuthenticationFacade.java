package com.play.quiz.security;

import com.play.quiz.domain.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

public interface AuthenticationFacade {

    User getPrincipal();

    Account getAccount();

    Authentication getAuthentication();
}
