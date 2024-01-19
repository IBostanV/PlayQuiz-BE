package com.play.quiz.controller;

import static com.play.quiz.controller.RestEndpoint.REQUEST_MAPPING_CATEGORY;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.play.quiz.ApiWebServer;
import com.play.quiz.TestAppContextInitializer;
import com.play.quiz.domain.Category;
import com.play.quiz.dto.CategoryDto;
import com.play.quiz.exception.RecordNotFoundException;
import com.play.quiz.fixtures.CategoryFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@SpringBootTest(classes = ApiWebServer.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {TestAppContextInitializer.class})
class CategoryControllerIT {

    @Value("${application.security.jwt.token}")
    private String token;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @AfterEach
    void tearDown() {
        namedParameterJdbcTemplate.update("DELETE FROM Q_CATEGORY WHERE CAT_ID IS NOT NULL", Collections.emptyMap());
        namedParameterJdbcTemplate.update("ALTER SEQUENCE CATEGORIES_SEQ RESTART", Collections.emptyMap());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql("/scripts/add_category.sql")
    void given_categoryId_when_get_category_then_return_category() throws Exception {
        String body = "{\"catId\":3,\"parentId\":2,\"visible\":true,\"naturalId\":\"CONTINENT\",\"parentName\":\"Earth\",\"name\":\"Continent\"}";
        Category category = CategoryFixture.getCategory();

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
    @Sql("/scripts/add_category.sql")
    void given_naturalId_when_get_category_then_return_category() throws Exception {
        String naturalId = "CONTINENT";
        String body = "{\"catId\":3,\"parentId\":2,\"visible\":true,\"naturalId\":\"CONTINENT\",\"parentName\":\"Earth\",\"name\":\"Continent\"}";

        Category category = CategoryFixture.getCategory();

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
    void given_null_categoryId_when_get_category_then_record_not_found_exception() throws Exception {
        String body = "No records found by natural Id: null";

        mockMvc.perform(MockMvcRequestBuilders.get(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_CATEGORY)
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .param("id", (String) null))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertInstanceOf(RecordNotFoundException.class, result.getResolvedException()))
                .andExpect(content().string(body));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql("/scripts/add_category.sql")
    void when_getAllCategories_then_return_all_categories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_CATEGORY + "/all-categories")
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(result -> {
                    CategoryDto[] categoryDtos = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryDto[].class);
                    assertEquals(2, categoryDtos.length);
                    assertEquals("EARTH", categoryDtos[0].getNaturalId());
                    assertEquals("Earth", categoryDtos[0].getName());
                    assertEquals(true, categoryDtos[0].getVisible());
                    assertEquals(2, categoryDtos[0].getCatId());
                    assertNull(categoryDtos[0].getParentId());
                    assertNull(categoryDtos[0].getParentName());

                    assertEquals("CONTINENT", categoryDtos[1].getNaturalId());
                    assertEquals("Continent", categoryDtos[1].getName());
                    assertEquals(3, categoryDtos[1].getCatId());
                    assertEquals("Earth", categoryDtos[1].getParentName());
                    assertEquals(2, categoryDtos[1].getParentId());
                    assertTrue(categoryDtos[1].getVisible());
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void given_no_categories_when_getAllCategories_then_return_all_categories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_CATEGORY + "/all-categories")
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(result -> {
                    assertEquals("[ ]", result.getResponse().getContentAsString());
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql("/scripts/add_category.sql")
    void given_existing_category_when_save_then_update_existing_category() throws Exception {
        String content = "{\"catId\":3,\"parentId\":2,\"visible\":true,\"naturalId\":\"CONTINENT\",\"parentName\":\"Earth\",\"categoryTranslations\":null,\"name\":\"Continent\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_CATEGORY)
                        .with(csrf())
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().is2xxSuccessful())
                .andExpect(result -> {
                    CategoryDto categoryDto = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryDto.class);
                    assertEquals(CategoryFixture.getCategoryDto(), categoryDto);
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql("/scripts/add_category.sql")
    void given_new_category_when_save_then_save_category() throws Exception {
        String content = "{\"parentId\":2,\"visible\":true,\"naturalId\":\"OCEAN\",\"parentName\":\"Earth\",\"categoryTranslations\":null,\"name\":\"Ocean\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_CATEGORY)
                        .with(csrf())
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                        .content(content))
                .andExpect(status().is2xxSuccessful())
                .andExpect(result -> {
                    CategoryDto categoryDto = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryDto.class);
                    assertEquals(CategoryFixture.getNewCategory(), categoryDto);
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void given_no_content_when_save_then_throw_validation_exception() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_CATEGORY)
                        .with(csrf())
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE))
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException))
                .andExpect(content().string(containsString("Required request body is missing")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void given_not_all_mandatory_fields_present_when_save_then_throw_validation_exception() throws Exception {
        String body = "{\"catId\":7,\"parentId\":1,\"visible\":true,\"naturalId\":\"CONTINENT\",\"parentName\":\"Earth\",\"categoryTranslations\":null}";

        mockMvc.perform(MockMvcRequestBuilders.post(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_CATEGORY)
                        .with(csrf())
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("name must not be blank")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void given_categoryId_when_deleteCategory_then_category_deleted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_CATEGORY + "/" + 7)
                        .with(csrf())
                        .accept(APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }
}
