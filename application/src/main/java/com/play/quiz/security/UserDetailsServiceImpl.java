package com.play.quiz.security;

import com.play.quiz.exception.UserNotFoundException;
import com.play.quiz.model.Account;
import com.play.quiz.model.Role;
import com.play.quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("quizUserDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadByEmail(final String userEmail) {
        final Account account = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("No user found with email: " + userEmail));
        return buildUserDetails(account);
    }

    private static UserDetails buildUserDetails(final Account account) {
        return User.builder()
                .username(account.getEmail())
                .password(account.getPassword())
                .authorities(getUserAuthorities(account))
                .build();
    }

    private static List<? extends GrantedAuthority> getUserAuthorities(final Account account) {
        return account.getRoles().stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        return loadByEmail(user);
    }
}
