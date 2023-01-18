package com.play.quiz.service;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.exception.AccountDisabledException;
import com.play.quiz.exception.DuplicateUserException;
import com.play.quiz.fixtures.AccountFixture;
import com.play.quiz.fixtures.AccountInfoFixture;
import com.play.quiz.mapper.AccountMapper;
import com.play.quiz.model.Account;
import com.play.quiz.model.helpers.AccountInfo;
import com.play.quiz.security.jwt.JwtProvider;
import com.play.quiz.service.impl.AuthenticationServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private UserService userService;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Captor
    private ArgumentCaptor<String> accountCaptor;

    @Captor
    private ArgumentCaptor<Account> accountDtoCaptor;

    @Captor
    private ArgumentCaptor<Authentication> authenticationArgumentCaptor;

    @Captor
    private ArgumentCaptor<UsernamePasswordAuthenticationToken> usernamePasswordAuthenticationTokenArgumentCaptor;

    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void init() {
        authenticationService = new AuthenticationServiceImpl(jwtProvider, userService, accountMapper, authenticationManager);
    }

    @Test
    void given_valid_credentials_when_login_then_login_user() {
        final AccountInfo accountInfo = AccountInfoFixture.getAccountInfo();
        final Account adminAccount = AccountFixture.getAdminAccount();
        final Authentication authentication = new UsernamePasswordAuthenticationToken(
                accountInfo.getAccount().getEmail(),
                accountInfo.getAccount().getPassword());

        commonWhenClauses(accountInfo, adminAccount, authentication);
        when(userService.findByEmail(accountInfo.getAccount().getEmail())).thenReturn(adminAccount);

        AccountInfo result = authenticationService.login(accountInfo.getAccount());

        DefaultCapture captures = defaultCaptures();
        Mockito.verify(userService).findByEmail(accountCaptor.capture());
        String userEmail = accountCaptor.getValue();

        assertEquals(result, accountInfo);
        assertEquals(captures.getAccount(), adminAccount);
        assertEquals(captures.getAuthentication(), authentication);
        assertEquals(captures.getUsernamePasswordAuthenticationToken(), authentication);
        assertEquals(userEmail, accountInfo.getAccount().getEmail());

        verify(userService, only()).findByEmail(accountInfo.getAccount().getEmail());
        verify(jwtProvider, only()).generate(authentication);
        verify(accountMapper, only()).toDto(adminAccount);
        verify(authenticationManager, only()).authenticate(any());
    }

    @Test
    void given_disabled_account_when_login_then_AccountDisabledException_thrown() {
        final AccountInfo accountInfo = AccountInfoFixture.getAccountInfoWithDisabledAccount();
        final Account adminAccount = AccountFixture.getDisabledAdminAccount();
        final Authentication authentication = new UsernamePasswordAuthenticationToken(
                accountInfo.getAccount().getEmail(),
                accountInfo.getAccount().getPassword());

        when(userService.findByEmail(accountInfo.getAccount().getEmail())).thenReturn(adminAccount);

        AccountDisabledException accountDisabledException = assertThrows(AccountDisabledException.class,
                () -> authenticationService.login(accountInfo.getAccount()),
                "Expected authenticationService.login() to throw AccountDisabledException."
        );

        assertTrue(accountDisabledException.getMessage().contains("Account " + adminAccount.getEmail() + " is disabled"));

        verify(userService, only()).findByEmail(accountInfo.getAccount().getEmail());
        verify(jwtProvider, never()).generate(authentication);
        verify(accountMapper, never()).toDto(adminAccount);
        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    void given_account_no_email_when_login_then_exception() {
        final AccountInfo accountInfo = AccountInfoFixture.getAccountInfoWithNoEmailAccount();
        final Account adminAccount = AccountFixture.getDisabledAdminAccount();
        final Authentication authentication = new UsernamePasswordAuthenticationToken(
                accountInfo.getAccount().getEmail(),
                accountInfo.getAccount().getPassword());

        NullPointerException nullPointerException = assertThrows(NullPointerException.class,
                () -> authenticationService.login(accountInfo.getAccount()),
                "Expected authenticationService.login() to throw NullPointerException."
        );

        assertTrue(nullPointerException.getMessage().contains("\"account\" is null"));

        verify(userService, only()).findByEmail(accountInfo.getAccount().getEmail());
        verify(jwtProvider, never()).generate(authentication);
        verify(accountMapper, never()).toDto(adminAccount);
        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    void given_valid_account_when_register_then_return_AccountInfo() {
        final AccountInfo accountInfo = AccountInfoFixture.getAccountInfo();
        final AccountDto accountDto = accountInfo.getAccount();
        final Account adminAccount = AccountFixture.getAdminAccount();
        final Authentication authentication = new UsernamePasswordAuthenticationToken(
                accountInfo.getAccount().getEmail(),
                accountInfo.getAccount().getPassword());

        commonWhenClauses(accountInfo, adminAccount, authentication);
        when(userService.userExists(accountInfo.getAccount())).thenReturn(false);
        when(userService.save(accountDto)).thenReturn(adminAccount);

        AccountInfo result = authenticationService.register(accountInfo.getAccount());

        DefaultCapture captures = defaultCaptures();

        assertEquals(result, accountInfo);
        assertEquals(captures.getAccount(), adminAccount);
        assertEquals(captures.getAuthentication(), authentication);
        assertEquals(captures.getUsernamePasswordAuthenticationToken(), authentication);

        verify(jwtProvider, only()).generate(authentication);
        verify(accountMapper, only()).toDto(adminAccount);
        verify(authenticationManager, only()).authenticate(any());
    }

    @Test
    void given_valid_account_when_register_with_email_then_return_AccountInfo() {
        final AccountInfo accountInfo = AccountInfoFixture.getAccountInfo();
        final AccountDto accountDto = accountInfo.getAccount();
        final Account adminAccount = AccountFixture.getAdminAccount();
        final Authentication authentication = new UsernamePasswordAuthenticationToken(
                accountInfo.getAccount().getEmail(),
                accountInfo.getAccount().getPassword());

        commonWhenClauses(accountInfo, adminAccount, authentication);
        when(userService.userExists(accountInfo.getAccount())).thenReturn(false);
        when(userService.save(accountDto)).thenReturn(adminAccount);
        doNothing().when(userService).sendAccountVerificationEmail(adminAccount);

        AccountInfo result = authenticationService.register(accountInfo.getAccount());

        DefaultCapture captures = defaultCaptures();

        assertEquals(result, accountInfo);
        assertEquals(captures.getAccount(), adminAccount);
        assertEquals(captures.getAuthentication(), authentication);
        assertEquals(captures.getUsernamePasswordAuthenticationToken(), authentication);

        verify(jwtProvider, only()).generate(authentication);
        verify(accountMapper, only()).toDto(adminAccount);
        verify(authenticationManager, only()).authenticate(authentication);
        verify(userService, times(1)).sendAccountVerificationEmail(adminAccount);
    }

    @Test
    void given_existing_account_when_userExists_then_DuplicateUserException_thrown() {
        final AccountInfo accountInfo = AccountInfoFixture.getAccountInfo();
        final AccountDto accountDto = accountInfo.getAccount();
        final Account adminAccount = AccountFixture.getAdminAccount();
        final Authentication authentication = new UsernamePasswordAuthenticationToken(
                accountInfo.getAccount().getEmail(),
                accountInfo.getAccount().getPassword());

        when(userService.userExists(accountDto)).thenReturn(true);

        DuplicateUserException duplicateUserException = assertThrows(DuplicateUserException.class,
                () -> authenticationService.register(accountDto),
                "Expected authenticationService.register() to throw DuplicateUserException."
        );

        assertTrue(duplicateUserException.getMessage().contains("User " + accountDto.getEmail() + " already exists"));

        verify(userService, only()).userExists(accountDto);
        verify(jwtProvider, never()).generate(authentication);
        verify(accountMapper, never()).toDto(adminAccount);
        verify(authenticationManager, never()).authenticate(any());
    }

    @Getter
    @AllArgsConstructor
    private static class DefaultCapture {
        private Account account;
        private Authentication authentication;
        private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
    }

    private void commonWhenClauses(final AccountInfo accountInfo, final Account adminAccount, final Authentication authentication) {
        when(jwtProvider.generate(any())).thenReturn(accountInfo.getJwtToken());
        when(accountMapper.toDto(adminAccount)).thenReturn(accountInfo.getAccount());
        when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
    }

    private DefaultCapture defaultCaptures() {
        Mockito.verify(accountMapper).toDto(accountDtoCaptor.capture());
        Account accountDtoCaptorValue = accountDtoCaptor.getValue();

        Mockito.verify(jwtProvider).generate(authenticationArgumentCaptor.capture());
        Authentication authenticationArgumentCaptorValue = authenticationArgumentCaptor.getValue();

        Mockito.verify(authenticationManager).authenticate(usernamePasswordAuthenticationTokenArgumentCaptor.capture());
        UsernamePasswordAuthenticationToken token = usernamePasswordAuthenticationTokenArgumentCaptor.getValue();

        return new DefaultCapture(accountDtoCaptorValue, authenticationArgumentCaptorValue, token);
    }
}
