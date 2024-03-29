package com.play.quiz.service.impl;

import com.play.quiz.domain.Glossary;
import com.play.quiz.domain.GlossaryType;
import com.play.quiz.dto.GlossaryDto;
import com.play.quiz.dto.GlossaryTypeDto;
import com.play.quiz.exception.EntityNotUpdatedException;
import com.play.quiz.exception.RecordNotFoundException;
import com.play.quiz.mapper.GlossaryMapper;
import com.play.quiz.mapper.GlossaryTypeMapper;
import com.play.quiz.repository.GlossaryRepository;
import com.play.quiz.repository.GlossaryTypeRepository;
import com.play.quiz.service.GlossaryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static com.play.quiz.util.Constant.SAVE_OPERATION_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
public class GlossaryServiceImpl implements GlossaryService {

    private final GlossaryMapper glossaryMapper;
    private final GlossaryRepository glossaryRepository;
    private final GlossaryTypeMapper glossaryTypeMapper;
    private final GlossaryTypeRepository glossaryTypeRepository;

    @Transactional
    public GlossaryDto save(final GlossaryDto glossaryDto, final MultipartFile attachment) {
        Glossary glossary = glossaryMapper.toEntity(glossaryDto, attachment);

        if (Objects.nonNull(attachment) || Objects.isNull(glossaryDto.getTermId())) {
            Glossary entity = glossaryRepository.save(glossary);
            return glossaryMapper.toDto(entity);
        }

        return saveWithoutAttachment(glossary);
    }

    private GlossaryDto saveWithoutAttachment(final Glossary glossary) {
        int savedWithoutAttachmentResult = glossaryRepository.saveWithoutAttachment(glossary);

        if (!Objects.equals(savedWithoutAttachmentResult, SAVE_OPERATION_SUCCESSFULLY)) {
            throw new EntityNotUpdatedException("Exception during glossary saving");
        }

        return glossaryMapper.toDto(glossaryRepository.getReferenceById(glossary.getTermId()));
    }

    @Override
    public GlossaryDto getById(final Long glossaryId) {
        Glossary glossary = glossaryRepository.findById(glossaryId)
                .orElseThrow(() -> new RecordNotFoundException("No records found by glossary id: " + glossaryId));
        return glossaryMapper.toDto(glossary);
    }

    @Override
    public GlossaryDto getByKey(String glossaryKey) {
        Glossary glossary = glossaryRepository.findByKey(glossaryKey)
                .orElseThrow(() -> new RecordNotFoundException("No records found by key: " + glossaryKey));
        return glossaryMapper.toDto(glossary);
    }

    @Override
    @Transactional
    public List<GlossaryDto> getByCategoryId(final Long categoryId) {
        List<Glossary> glossaryList = glossaryRepository.findHierarchicalByCategoryId(categoryId)
                .orElseThrow(() -> new RecordNotFoundException("No glossary found by category id: " + categoryId));
        return glossaryMapper.toDto(glossaryList);
    }

    @NonNull
    @Override
    @Transactional
    public Integer toggleGlossary(final Long glossaryId) {
        return glossaryRepository.toggleGlossary(glossaryId);
    }

    @Override
    public GlossaryType saveGlossaryType(final GlossaryType glossaryType) {
        return glossaryTypeRepository.save(glossaryType);
    }

    @Override
    public List<GlossaryTypeDto> getGlossaryTypes() {
        List<GlossaryType> glossaryTypeList = glossaryTypeRepository.findAll();
        return glossaryTypeMapper.toDtoList(glossaryTypeList);
    }

    @Override
    public Glossary getEntityById(final Long glossaryId) {
        return glossaryRepository.getReferenceById(glossaryId);
    }
}
