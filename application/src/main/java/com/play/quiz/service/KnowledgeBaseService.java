package com.play.quiz.service;

import java.util.List;

import com.play.quiz.dto.KnowledgeBaseRecordDto;
import org.springframework.web.multipart.MultipartFile;

public interface KnowledgeBaseService {

    KnowledgeBaseRecordDto save(KnowledgeBaseRecordDto knowledgeBaseRecordDto, MultipartFile attachment);

    List<KnowledgeBaseRecordDto> getAllRecords();
}
