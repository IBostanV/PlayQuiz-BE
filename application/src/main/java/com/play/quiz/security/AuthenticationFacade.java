package com.play.quiz.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

public interface AuthenticationFacade {

    Authentication getAuthentication();

    User getPrincipal();
}
