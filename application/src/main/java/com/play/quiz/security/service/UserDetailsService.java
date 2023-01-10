package com.play.quiz.security.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {

    UserDetails loadByEmail(final String email);
}
