package com.play.quiz.security;

import com.play.quiz.exception.UserNotFoundException;
import com.play.quiz.model.Account;
import com.play.quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("quizUserDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadByEmail(final String userEmail) {
        final Account account = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("No user found with email: " + userEmail));
        return User.builder()
                .username(userEmail)
                .password(account.getPassword())
                .authorities(getUserAuthorities())
                .build();
    }

    private static List<? extends GrantedAuthority> getUserAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        return loadByEmail(user);
    }
}
