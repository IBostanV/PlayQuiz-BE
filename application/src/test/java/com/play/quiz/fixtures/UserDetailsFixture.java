package com.play.quiz.fixtures;

import com.play.quiz.enums.UserRole;
import com.play.quiz.domain.Account;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsFixture {
    public static UserDetails getAdminUserDetails() {
        final Account account = AccountFixture.getAdminAccount();
        final SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.name());

        return User.builder()
                .username(account.getEmail())
                .password(new String(account.getPassword()))
                .authorities(simpleGrantedAuthority)
                .build();
    }
}
