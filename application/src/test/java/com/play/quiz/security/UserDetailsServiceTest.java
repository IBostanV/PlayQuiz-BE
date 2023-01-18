package com.play.quiz.security;

import com.play.quiz.enums.UserRole;
import com.play.quiz.exception.UserNotFoundException;
import com.play.quiz.fixtures.AccountFixture;
import com.play.quiz.fixtures.UserDetailsFixture;
import com.play.quiz.model.Account;
import com.play.quiz.repository.UserRepository;
import com.play.quiz.security.service.UserDetailsService;
import com.play.quiz.security.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {
    private UserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<String> emailCaptor;

    @BeforeEach
    public void init() {
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    void give_userEmail_when_loadByEmail_then_return_userDetails() {
        final String userEmail = "vanyok93@yahoo.com";
        final Account adminAccount = AccountFixture.getAdminAccount();
        final UserDetails adminUserDetails = UserDetailsFixture.getAdminUserDetails();

        when(userRepository.findUserByEmail(userEmail)).thenReturn(Optional.of(adminAccount));

        UserDetails userDetails = userDetailsService.loadByEmail(userEmail);

        Mockito.verify(userRepository).findUserByEmail(emailCaptor.capture());
        String emailCaptureValue = emailCaptor.getValue();

        assertEquals(userDetails, adminUserDetails);
        assertEquals(emailCaptureValue, userEmail);

        verify(userRepository, only()).findUserByEmail(userEmail);
    }

    @Test
    void give_no_roles_account_when_loadByEmail_then_return_userDetails_with_role_user() {
        final String userEmail = "vanyok93@yahoo.com";
        final Account adminAccount = AccountFixture.getNoRolesAccount();
        final UserDetails adminUserDetails = UserDetailsFixture.getAdminUserDetails();
        final SimpleGrantedAuthority userRole = new SimpleGrantedAuthority(UserRole.ROLE_USER.name());
        final SimpleGrantedAuthority adminRole = new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.name());

        when(userRepository.findUserByEmail(userEmail)).thenReturn(Optional.of(adminAccount));

        UserDetails userDetails = userDetailsService.loadByEmail(userEmail);

        Mockito.verify(userRepository).findUserByEmail(emailCaptor.capture());
        String emailCaptureValue = emailCaptor.getValue();

        assertEquals(userDetails, adminUserDetails);
        assertEquals(emailCaptureValue, userEmail);
        assertTrue(userDetails.getAuthorities().contains(userRole));
        assertFalse(userDetails.getAuthorities().contains(adminRole));

        verify(userRepository, only()).findUserByEmail(userEmail);
    }

    @Test
    void give_no_email_when_loadByEmail_then_UserNotFoundException_thrown() {
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class,
                () -> userDetailsService.loadByEmail(null),
                "Expected authenticationService.login() to throw UserNotFoundException."
        );

        assertEquals("No user found with email: null", userNotFoundException.getMessage());
    }
}
