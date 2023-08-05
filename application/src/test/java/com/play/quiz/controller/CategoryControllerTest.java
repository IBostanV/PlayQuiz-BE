package com.play.quiz.controller;

import static com.play.quiz.controller.RestEndpoint.REQUEST_MAPPING_CATEGORY;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import java.util.Optional;

import com.play.quiz.TestAppContextInitializer;
import com.play.quiz.exception.RecordNotFoundException;
import com.play.quiz.fixtures.AccountFixture;
import com.play.quiz.fixtures.CategoryFixture;
import com.play.quiz.model.Account;
import com.play.quiz.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@WebMvcTest(value = CategoryController.class)
@ComponentScan(basePackages = {"com.play.quiz"})
@ContextConfiguration(initializers = {TestAppContextInitializer.class})
class CategoryControllerTest {
    @Value("${application.security.jwt.token}")
    private String token;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void given_categoryId_when_get_category_then_return_category() throws Exception {
        final Long categoryId = 7L;
        final String body = "{\"catId\":"+ categoryId +",\"naturalId\":\"CONTINENT\",\"parent\":null,\"name\":\"Continent\",\"categoryTranslations\":null}";

        Map<String, Object> anyMap = ArgumentMatchers.any();
        BeanPropertyRowMapper<Account> rowMapper = ArgumentMatchers.any();

        when(namedParameterJdbcTemplate.queryForObject(any(), anyMap, rowMapper))
                .thenReturn(AccountFixture.getAdminAccount());
        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.ofNullable(CategoryFixture.getCategory()));

        mockMvc.perform(MockMvcRequestBuilders.get(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_CATEGORY)
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .param("id", categoryId.toString()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.catId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.catId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.naturalId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.parent").isEmpty())
                .andExpect(content().string(body));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void given_naturalId_when_get_category_then_return_category() throws Exception {
        final long categoryId = 7L;
        final String naturalId = "COUNTRY";
        final String body = "{\"catId\":"+ categoryId +",\"naturalId\":\"CONTINENT\",\"parent\":null,\"name\":\"Continent\",\"categoryTranslations\":null}";

        Map<String, Object> anyMap = ArgumentMatchers.any();
        BeanPropertyRowMapper<Account> rowMapper = ArgumentMatchers.any();

        when(namedParameterJdbcTemplate.queryForObject(any(), anyMap, rowMapper))
                .thenReturn(AccountFixture.getAdminAccount());
        when(categoryRepository.findByNaturalId(naturalId))
                .thenReturn(Optional.ofNullable(CategoryFixture.getCategory()));

        mockMvc.perform(MockMvcRequestBuilders.get(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_CATEGORY)
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .param("naturalId", naturalId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.catId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.catId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.naturalId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.parent").isEmpty())
                .andExpect(content().string(body));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void given_null_categoryId_when_get_category_then_return_category() throws Exception {
        final String body = "No records found by natural Id: null";

        Map<String, Object> anyMap = ArgumentMatchers.any();
        BeanPropertyRowMapper<Account> rowMapper = ArgumentMatchers.any();

        when(namedParameterJdbcTemplate.queryForObject(any(), anyMap, rowMapper))
                .thenReturn(AccountFixture.getAdminAccount());

        mockMvc.perform(MockMvcRequestBuilders.get(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_CATEGORY)
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .param("id", (String) null))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
                .andExpect(content().string(body));
    }
}
