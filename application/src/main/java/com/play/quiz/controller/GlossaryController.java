package com.play.quiz.controller;

import com.play.quiz.dto.GlossaryDto;
import com.play.quiz.service.GlossaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(RestEndpoint.CONTEXT_PATH + "/glossary")
@RequiredArgsConstructor
public class GlossaryController {
    private final GlossaryService glossaryService;

    @GetMapping(value = "/id/{glossaryId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GlossaryDto> getGlossaryById(@PathVariable final Long glossaryId) {
        return ResponseEntity.ok()
                .body(glossaryService.getById(glossaryId));
    }

    @GetMapping(value = "/key/{glossaryKey}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GlossaryDto> getGlossaryByKey(@PathVariable final String glossaryKey) {
        return ResponseEntity.ok()
                .body(glossaryService.getByKey(glossaryKey));
    }

    @GetMapping(value = "/category/{categoryId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GlossaryDto> getGlossaryByCategory(@PathVariable final Long categoryId) {
        return ResponseEntity.ok()
                .body(glossaryService.getByCategoryId(categoryId));
    }

    @PatchMapping(value = "/save",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GlossaryDto> saveGlossary(@Valid @RequestPart(name = "glossary") final GlossaryDto glossaryDto,
                                                    @RequestPart(required = false) final MultipartFile attachment) {
        final GlossaryDto responseBody = glossaryService.save(glossaryDto,attachment);

        return ResponseEntity.ok(responseBody);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> toggleGlossary(@RequestParam final Long id) {
        glossaryService.toggleGlossary(id);
        return ResponseEntity.ok().build();
    }
}
