package com.play.quiz.controller;

import com.play.quiz.dto.CategoryDto;
import com.play.quiz.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping(RestEndpoint.CONTEXT_PATH + "/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> getCategory(@RequestParam(required = false) final Long id,
                                                   @RequestParam(required = false) final String naturalId) {
        if (Objects.nonNull(id)) {
            return ResponseEntity.ok(categoryService.getById(id));
        }
        return ResponseEntity.ok(categoryService.getByNaturalId(naturalId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> saveCategory(@Valid @RequestBody final CategoryDto requestCategory) {
        final CategoryDto categoryDto = categoryService.save(requestCategory);

        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable final Long categoryId) {
        categoryService.deleteById(categoryId);
        return ResponseEntity.ok().build();
    }
}
