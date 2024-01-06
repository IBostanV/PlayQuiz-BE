package com.play.quiz.controller;

import static com.play.quiz.controller.RestEndpoint.REQUEST_MAPPING_CATEGORY;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_CATEGORY)
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> getCategory(@RequestParam(required = false) Long id,
                                                   @RequestParam(required = false) String naturalId) {
        return ResponseEntity.ok(Objects.nonNull(id)
                ? categoryService.getById(id)
                : categoryService.getByNaturalId(naturalId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> saveCategory(@Valid @RequestPart(name = "request") final CategoryDto requestCategory,
                                                    @RequestPart(required = false) final MultipartFile attachment) {
        return ResponseEntity.ok(categoryService.save(requestCategory, attachment));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable final Long categoryId) {
        categoryService.deleteById(categoryId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/get-all-categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getCategories();
        return ResponseEntity.ok(addRefLinks(categories));
    }

    private List<CategoryDto> addRefLinks(List<CategoryDto> categories) {
        categories.forEach(this::addRefLink);
        return categories;
    }

    private void addRefLink(CategoryDto categoryDto) {
        categoryDto
                .add(linkTo(methodOn(this.getClass())
                        .getCategory(categoryDto.getCatId(), categoryDto.getNaturalId()))
                        .withSelfRel())
                .add(linkTo(methodOn(this.getClass())
                        .deleteCategory(categoryDto.getCatId()))
                        .withRel("delete"));
    }
}
