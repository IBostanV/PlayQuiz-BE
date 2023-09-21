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
import com.play.quiz.domain.Category;
import com.play.quiz.exception.RecordNotFoundException;
import com.play.quiz.fixtures.AccountFixture;
import com.play.quiz.fixtures.CategoryFixture;
import com.play.quiz.domain.Account;
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
    private CategoryRepository categoryRepository;

    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    @WithMockUser(roles = "ADMIN")
    void given_categoryId_when_get_category_then_return_category() throws Exception {
        String body = "{\"catId\":7,\"parentId\":1,\"visible\":true,\"naturalId\":\"CONTINENT\",\"parentName\":\"Earth\",\"name\":\"Continent\"}";

        Category category = CategoryFixture.getCategory();
        Map<String, Object> anyMap = ArgumentMatchers.any();
        BeanPropertyRowMapper<Account> rowMapper = ArgumentMatchers.any();

        when(namedParameterJdbcTemplate.queryForObject(any(), anyMap, rowMapper))
                .thenReturn(AccountFixture.getAdminAccount());
        when(categoryRepository.findById(category.getCatId())).thenReturn(Optional.of(category));

        mockMvc.perform(MockMvcRequestBuilders.get(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_CATEGORY)
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .param("id", category.getCatId().toString()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.catId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.catId").value(category.getCatId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(category.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.naturalId").value(category.getNaturalId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parentId").value(category.getParent().getCatId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parentName").value(category.getParent().getName()))
                .andExpect(content().json(body));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void given_naturalId_when_get_category_then_return_category() throws Exception {
        String naturalId = "COUNTRY";
        String body = "{\"catId\":7,\"parentId\":1,\"visible\":true,\"naturalId\":\"CONTINENT\",\"parentName\":\"Earth\",\"name\":\"Continent\"}";

        Category category = CategoryFixture.getCategory();
        Map<String, Object> anyMap = ArgumentMatchers.any();
        BeanPropertyRowMapper<Account> rowMapper = ArgumentMatchers.any();

        when(namedParameterJdbcTemplate.queryForObject(any(), anyMap, rowMapper))
                .thenReturn(AccountFixture.getAdminAccount());
        when(categoryRepository.findByNaturalId(naturalId)).thenReturn(Optional.of(category));

        mockMvc.perform(MockMvcRequestBuilders.get(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_CATEGORY)
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .param("naturalId", naturalId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.catId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.catId").value(category.getCatId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(category.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.naturalId").value(category.getNaturalId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parentId").value(category.getParent().getCatId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parentName").value(category.getParent().getName()))
                .andExpect(content().json(body));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void given_null_categoryId_when_get_category_then_return_category() throws Exception {
        String body = "No records found by natural Id: null";

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
