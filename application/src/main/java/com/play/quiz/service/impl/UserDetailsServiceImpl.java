package com.play.quiz.service.impl;

import com.play.quiz.exception.NoSuchUserException;
import com.play.quiz.model.Account;
import com.play.quiz.repository.UserRepository;
import com.play.quiz.service.UserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("quizUserDetailsService")
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadByEmail(final String userEmail) {
        Account account = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new NoSuchUserException("No user found"));
        return User.builder()
                .username(userEmail)
                .password(account.getPassword())
                .authorities(getUserAuthorities())
                .build();
    }

    private static List<? extends GrantedAuthority> getUserAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        return loadByEmail(user);
    }
}
