package com.play.quiz.service.impl;

import com.play.quiz.dto.GlossaryDto;
import com.play.quiz.exception.RecordNotFoundException;
import com.play.quiz.mapper.GlossaryMapper;
import com.play.quiz.model.Glossary;
import com.play.quiz.repository.GlossaryRepository;
import com.play.quiz.service.GlossaryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class GlossaryServiceImpl implements GlossaryService {

    private final GlossaryRepository glossaryRepository;

    public GlossaryDto save(final GlossaryDto glossaryDto, final MultipartFile attachment) {
        Glossary glossary = GlossaryMapper.INSTANCE.toEntity(glossaryDto, attachment);
        return GlossaryMapper.INSTANCE.toDto(glossaryRepository.save(glossary), attachment);
    }

    @Override
    public GlossaryDto getById(final Long glossaryId) {
        Glossary glossary = glossaryRepository.findById(glossaryId)
                .orElseThrow(() -> new RecordNotFoundException("No records found by glossary id: " + glossaryId));
        return GlossaryMapper.INSTANCE.toDto(glossary);
    }

    @Override
    public GlossaryDto getByKey(final String glossaryKey) {
        Glossary glossary = glossaryRepository.findByKey(glossaryKey)
                .orElseThrow(() -> new RecordNotFoundException("No records found by category id: " + glossaryKey));
        return GlossaryMapper.INSTANCE.toDto(glossary);
    }

    @Override
    public GlossaryDto getByCategoryId(final Long categoryId) {
        Glossary glossary = glossaryRepository.findByCategory_catId(categoryId)
                .orElseThrow(() -> new RecordNotFoundException("No glossary found by category id: " + categoryId));
        return GlossaryMapper.INSTANCE.toDto(glossary);
    }

    @NonNull
    @Override
    @Transactional
    public void toggleGlossary(final Long glossaryId) {
        glossaryRepository.toggleGlossary(glossaryId);
    }
}
