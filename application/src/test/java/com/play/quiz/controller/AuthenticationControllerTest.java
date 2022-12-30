package com.play.quiz.controller;

import com.play.quiz.TestAppContextInitializer;
import com.play.quiz.fixtures.AccountFixture;
import com.play.quiz.fixtures.VerificationTokenFixture;
import com.play.quiz.model.Account;
import com.play.quiz.repository.VerificationTokenRepository;
import com.play.quiz.security.JwtProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(AuthenticationController.class)
@ComponentScan(basePackages = {"com.play.quiz"})
@ContextConfiguration(initializers = {TestAppContextInitializer.class})
@ExtendWith(OutputCaptureExtension.class)
public class AuthenticationControllerTest {
    private static final String CONTROLLER_PATH = "/auth";

    @Value("${application.domain.host.url}")
    private String redirectedURL;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private VerificationTokenRepository verificationTokenRepository;

    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void given_valid_credentials_when_login_then_successful() throws Exception {
        final String body = "{\"id\":1,\"name\":null,\"email\":\"vanyok93@yahoo.com\",\"birthday\":null,\"roles\":null,\"enabled\":false}";
        final String content = "{\"email\":\"vanyok93@yahoo.com\",\"password\":\"password\"}";

        Map<String, Object> anyMap = ArgumentMatchers.any();
        BeanPropertyRowMapper<Account> rowMapper = any();

        when(namedParameterJdbcTemplate.queryForObject(any(), anyMap, rowMapper))
                .thenReturn(AccountFixture.getAdminAccount());

        mockMvc.perform(post(CONTROLLER_PATH + "/login")
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .content(content))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(body))
                .andExpect(result -> assertNotNull(result.getResponse().getHeader(HttpHeaders.AUTHORIZATION)));
    }

    @Test
    public void given_invalid_credentials_when_login_then_validation_exception() throws Exception {
        String content = "{\"email\":\"\",\"password\":\"\"}";
        Map<String, Object> anyMap = ArgumentMatchers.any();
        BeanPropertyRowMapper<Account> rowMapper = any();

        when(namedParameterJdbcTemplate.queryForObject(any(), anyMap, rowMapper))
                .thenReturn(AccountFixture.getAdminAccount());

        mockMvc.perform(post(CONTROLLER_PATH + "/login")
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .content(content))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(Objects.requireNonNull(result.getResolvedException())
                        .getMessage().contains("default message [password]]; default message [must not be blank]")))
                .andExpect(result -> assertTrue(Objects.requireNonNull(result.getResolvedException())
                        .getMessage().contains("default message [email]]; default message [must not be blank]")))
                .andExpect(result -> assertTrue(Objects.requireNonNull(result.getResolvedException())
                        .getMessage().contains("Please provide a valid email address")));
    }

    @Test
    public void given_wrong_password_when_login_then_bad_credentials_exception(final CapturedOutput output) throws Exception {
        final String content = "{\"email\":\"vanyok93@yahoo.com\",\"password\":\"no_password\"}";
        final String passwordNotMatchMessage = "Failed to authenticate since password does not match stored value";

        Map<String, Object> anyMap = ArgumentMatchers.any();
        BeanPropertyRowMapper<Account> rowMapper = any();

        when(namedParameterJdbcTemplate.queryForObject(any(), anyMap, rowMapper))
                .thenReturn(AccountFixture.getAdminAccount());

        mockMvc.perform(post(CONTROLLER_PATH + "/login")
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .content(content))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadCredentialsException))
                .andExpect(content().string("Bad credentials"))
                .andExpect(result -> Assertions.assertTrue(output.getOut().contains(passwordNotMatchMessage)));
    }

    @Test
    public void given_null_content_when_login_then_bad_credentials_exception(final CapturedOutput output) throws Exception {
        final String missingBody = "Required request body is missing";

        Map<String, Object> anyMap = ArgumentMatchers.any();
        BeanPropertyRowMapper<Account> rowMapper = any();

        when(namedParameterJdbcTemplate.queryForObject(any(), anyMap, rowMapper))
                .thenReturn(AccountFixture.getAdminAccount());

        mockMvc.perform(post(CONTROLLER_PATH + "/login")
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains(missingBody)))
                .andExpect(result -> assertTrue(output.getOut().contains(missingBody)));
    }

    @Test
    public void given_valid_credentials_when_register_then_user_registered(final CapturedOutput output) throws Exception {
        final String username = "vanyok93@yahoo.com";
        final String content = "{\"email\":\"" + username + "\",\"password\":\"password\"}";
        final String bodyMessage = "{\"id\":1,\"name\":null,\"email\":\"vanyok93@yahoo.com\",\"birthday\":null,\"roles\":null,\"enabled\":false}";

        Map<String, Object> anyMap = ArgumentMatchers.any();
        BeanPropertyRowMapper<Account> rowMapper = any();

        when(namedParameterJdbcTemplate.queryForObject(any(), anyMap, rowMapper)).thenReturn(null, AccountFixture.getAdminAccount());
        when(jdbcTemplate.queryForObject(any(), ArgumentMatchers.eq(Long.class))).thenReturn(1L);
        when(namedParameterJdbcTemplate.update(any(), any(), any())).thenReturn(1);
        when(verificationTokenRepository.save(any())).thenReturn(VerificationTokenFixture.getVerificationToken());

        mockMvc.perform(post(CONTROLLER_PATH + "/register")
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .content(content))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(bodyMessage))
                .andExpect(result -> Assertions.assertTrue(output.getOut().contains("Authenticated user")))
                .andExpect(result -> assertTrue(Objects.nonNull(result.getResponse().getHeader(HttpHeaders.AUTHORIZATION))))
                .andExpect(result -> assertEquals(jwtProvider.getUsernameFromToken(result.getResponse().getHeader(HttpHeaders.AUTHORIZATION)), username));
    }

    @Test
    public void given_invalid_credentials_when_register_then_user_registered() throws Exception {
        final String content = "{\"email\":\"\",\"password\":\"\"}";

        Map<String, Object> anyMap = ArgumentMatchers.any();
        BeanPropertyRowMapper<Account> rowMapper = any();

        when(namedParameterJdbcTemplate.queryForObject(any(), anyMap, rowMapper)).thenReturn(null, AccountFixture.getAdminAccount());
        when(jdbcTemplate.queryForObject(any(), ArgumentMatchers.eq(Long.class))).thenReturn(1L);
        when(namedParameterJdbcTemplate.update(any(), any(), any())).thenReturn(1);
        when(verificationTokenRepository.save(any())).thenReturn(VerificationTokenFixture.getVerificationToken());

        mockMvc.perform(post(CONTROLLER_PATH + "/register")
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .content(content))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(Objects.requireNonNull(result.getResolvedException())
                        .getMessage().contains("default message [password]]; default message [must not be blank]")))
                .andExpect(result -> assertTrue(Objects.requireNonNull(result.getResolvedException())
                        .getMessage().contains("default message [email]]; default message [must not be blank]")))
                .andExpect(result -> assertTrue(Objects.requireNonNull(result.getResolvedException())
                        .getMessage().contains("Please provide a valid email address")));
        ;
    }

    @Test
    public void given_activation_toke_when_activateAccount_then_redirected_successfully() throws Exception {
        final String token = "verification_token";

        Map<String, Object> anyMap = ArgumentMatchers.any();
        BeanPropertyRowMapper<Account> rowMapper = any();

        when(namedParameterJdbcTemplate.queryForObject(any(), anyMap, rowMapper)).thenReturn(AccountFixture.getAdminAccount());
        when(jdbcTemplate.queryForObject(any(), ArgumentMatchers.eq(Long.class))).thenReturn(1L);
        when(verificationTokenRepository.findByToken(token)).thenReturn(VerificationTokenFixture.getVerificationToken());

        mockMvc.perform(get(CONTROLLER_PATH + "/activate-account")
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .param("token", token))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(result -> assertEquals(result.getResponse().getHeader("Location"), redirectedURL))
                .andExpect(result -> assertEquals(result.getResponse().getRedirectedUrl(), redirectedURL));
    }
}
