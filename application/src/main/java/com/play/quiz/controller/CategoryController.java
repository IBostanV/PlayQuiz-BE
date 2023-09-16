package com.play.quiz.controller;

import static com.play.quiz.controller.RestEndpoint.REQUEST_MAPPING_CATEGORY;

import java.util.List;
import java.util.Objects;

import com.play.quiz.dto.CategoryDto;
import com.play.quiz.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_CATEGORY)
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> getCategory(@RequestParam(required = false) Long id,
                                                   @RequestParam(required = false) String naturalId) {
        return ResponseEntity.ok(Objects.nonNull(id) ?
                categoryService.getById(id) :
                categoryService.getByNaturalId(naturalId));
    }

    @GetMapping(value = "/get-all-categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> saveCategory(@Valid @RequestBody final CategoryDto requestCategory) {
        return ResponseEntity.ok(categoryService.save(requestCategory));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable final Long categoryId) {
        categoryService.deleteById(categoryId);
        return ResponseEntity.ok().build();
    }
}
