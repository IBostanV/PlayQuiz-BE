package com.play.quiz.controller;

import static com.play.quiz.controller.RestEndpoint.REQUEST_MAPPING_KNOWLEDGE_BASE;

import java.util.List;

import com.play.quiz.dto.KnowledgeBaseRecordDto;
import com.play.quiz.service.KnowledgeBaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_KNOWLEDGE_BASE)
@RequiredArgsConstructor
public class KnowledgeBaseController {

    private final KnowledgeBaseService knowledgeBaseService;

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<KnowledgeBaseRecordDto> save(@Valid @RequestPart(name = "request") final KnowledgeBaseRecordDto recordDto,
                               @RequestPart(required = false) final MultipartFile attachment) {
        KnowledgeBaseRecordDto result = knowledgeBaseService.save(recordDto, attachment);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/all-records", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KnowledgeBaseRecordDto>> getAllRecords() {
        return ResponseEntity.ok(knowledgeBaseService.getAllRecords());
    }
}
