package com.play.quiz.security;

import com.play.quiz.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
class AuthenticationFacadeTest {
    private AuthenticationFacade authenticationFacade;

    @BeforeEach
    void init() {
        authenticationFacade = new AuthenticationFacadeImpl();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void given_admin_user_when_getAuthentication_then_return_authentication() {
        final Authentication mockAuthentication = SecurityContextHolder.getContext().getAuthentication();

        Authentication authentication = authenticationFacade.getAuthentication();

        assertNotNull(authentication);
        assertEquals(authentication, mockAuthentication);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void given_admin_user_when_getPrincipal_then_return_principal() {
        final SimpleGrantedAuthority admin = new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.name());
        final Authentication mockAuthentication = SecurityContextHolder.getContext().getAuthentication();

        User principal = authenticationFacade.getPrincipal();

        assertNotNull(principal);
        assertEquals(principal, mockAuthentication.getPrincipal());
        assertEquals(principal.getAuthorities(), Set.of(admin));
    }

    @Test
    void given_no_authentication_when_getAuthentication_then_return_null_authentication() {
        Authentication authentication = authenticationFacade.getAuthentication();

        assertNull(authentication);
    }
}
