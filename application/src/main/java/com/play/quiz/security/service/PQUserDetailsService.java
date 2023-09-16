package com.play.quiz.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface PQUserDetailsService extends UserDetailsService {

    UserDetails loadByEmail(String email);
}
