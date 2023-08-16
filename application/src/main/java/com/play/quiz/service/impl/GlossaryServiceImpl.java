package com.play.quiz.service.impl;

import java.util.List;
import java.util.Objects;

import com.play.quiz.dto.GlossaryDto;
import com.play.quiz.exception.EntityNotUpdatedException;
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

    private final GlossaryMapper glossaryMapper;
    private final GlossaryRepository glossaryRepository;

    @Transactional
    public GlossaryDto save(final GlossaryDto glossaryDto, final MultipartFile attachment) {
        Glossary glossary = glossaryMapper.toEntity(glossaryDto, attachment);
        return Objects.nonNull(attachment) || Objects.isNull(glossaryDto.getTermId())
                ? glossaryMapper.toDto(glossaryRepository.save(glossary)) : saveWithoutAttachment(glossary);
    }

    private GlossaryDto saveWithoutAttachment(final Glossary glossary) {
        int savedWithoutAttachment = glossaryRepository.saveWithoutAttachment(glossary);
        if (!Objects.equals(savedWithoutAttachment, 1)) {
            throw new EntityNotUpdatedException("Exception during glossary saving");
        }
        return glossaryMapper.toDto(glossary, null);
    }

    @Override
    public GlossaryDto getById(final Long glossaryId) {
        Glossary glossary = glossaryRepository.findById(glossaryId)
                .orElseThrow(() -> new RecordNotFoundException("No records found by glossary id: " + glossaryId));
        return glossaryMapper.toDto(glossary);
    }

    @Override
    public GlossaryDto getByKey(final String glossaryKey) {
        Glossary glossary = glossaryRepository.findByKey(glossaryKey)
                .orElseThrow(() -> new RecordNotFoundException("No records found by category id: " + glossaryKey));
        return glossaryMapper.toDto(glossary);
    }

    @Override
    @Transactional
    public List<GlossaryDto> getByCategoryId(final Long categoryId) {
        List<Glossary> glossaryList = glossaryRepository.findByCategory_catId(categoryId)
                .orElseThrow(() -> new RecordNotFoundException("No glossary found by category id: " + categoryId));
        return glossaryMapper.toDto(glossaryList);
    }

    @NonNull
    @Override
    @Transactional
    public Integer toggleGlossary(final Long glossaryId) {
        return glossaryRepository.toggleGlossary(glossaryId);
    }
}
