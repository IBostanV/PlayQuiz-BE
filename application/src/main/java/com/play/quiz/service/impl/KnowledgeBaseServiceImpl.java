package com.play.quiz.service.impl;

import java.util.List;

import com.play.quiz.domain.KnowledgeBaseRecord;
import com.play.quiz.dto.KnowledgeBaseRecordDto;
import com.play.quiz.mapper.KnowledgeBaseMapper;
import com.play.quiz.repository.KnowledgeBaseRepository;
import com.play.quiz.service.KnowledgeBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    private final KnowledgeBaseMapper knowledgeBaseMapper;
    private final KnowledgeBaseRepository knowledgeBaseRepository;

    @Override
    public KnowledgeBaseRecordDto save(KnowledgeBaseRecordDto knowledgeBaseRecordDto, MultipartFile attachment) {
        KnowledgeBaseRecord entity = knowledgeBaseMapper.toEntity(knowledgeBaseRecordDto, attachment);
        KnowledgeBaseRecord knowledgeBaseRecord = knowledgeBaseRepository.save(entity);
        return knowledgeBaseMapper.toDto(knowledgeBaseRecord);
    }

    @Override
    public List<KnowledgeBaseRecordDto> getAllRecords() {
        List<KnowledgeBaseRecord> knowledgeBaseRecords = knowledgeBaseRepository.findAll();
        return knowledgeBaseMapper.toDto(knowledgeBaseRecords);
    }
}
