package com.play.quiz.service.impl;

import static com.play.quiz.util.Constant.SAVE_OPERATION_SUCCESSFULLY;

import java.util.List;
import java.util.Objects;

import com.play.quiz.dto.GlossaryDto;
import com.play.quiz.exception.EntityNotUpdatedException;
import com.play.quiz.exception.RecordNotFoundException;
import com.play.quiz.mapper.GlossaryMapper;
import com.play.quiz.domain.Glossary;
import com.play.quiz.domain.GlossaryType;
import com.play.quiz.repository.GlossaryRepository;
import com.play.quiz.repository.GlossaryTypeRepository;
import com.play.quiz.service.GlossaryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class GlossaryServiceImpl implements GlossaryService {

    private final GlossaryMapper glossaryMapper;
    private final GlossaryRepository glossaryRepository;
    private final GlossaryTypeRepository glossaryTypeRepository;

    @Transactional
    public GlossaryDto save(final GlossaryDto glossaryDto, final MultipartFile attachment) {
        Glossary glossary = glossaryMapper.toEntity(glossaryDto, attachment);
        return Objects.nonNull(attachment) || Objects.isNull(glossaryDto.getTermId())
                ? glossaryMapper.toDto(glossaryRepository.save(glossary)) : saveWithoutAttachment(glossary);
    }

    private GlossaryDto saveWithoutAttachment(final Glossary glossary) {
        int savedWithoutAttachmentResult = glossaryRepository.saveWithoutAttachment(glossary);
        if (!Objects.equals(savedWithoutAttachmentResult, SAVE_OPERATION_SUCCESSFULLY)) {
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

    @Override
    public List<GlossaryDto> getLimitedByOptionWithoutSelf(int amount, final String option, final List<Long> rightAnswerIds) {
        Pageable pageable = PageRequest.of(0, amount);
        List<Glossary> glossaryList = glossaryRepository.getByOption(option, pageable, rightAnswerIds);

        return glossaryMapper.toDto(glossaryList);
    }

    @Override
    public List<GlossaryDto> getLimitedByOptions(int amount, final String... options) {
        Pageable pageable = PageRequest.of(0, amount);

        if (options.length == 2) {
            List<Glossary> glossaries = glossaryRepository.getByOptions(pageable, options[0], options[1]);
            return glossaryMapper.toDto(glossaries);
        }

        List<Glossary> glossaries = glossaryRepository.getByOptions(pageable, options[0], options[1], options[2]);
        return glossaryMapper.toDto(glossaries);
    }

    @Override
    public List<GlossaryDto> getLimitedByCategoryId(int amount, final Long catId) {
        Pageable pageable = PageRequest.of(0, amount);
        List<Glossary> glossaryList = glossaryRepository.findByCategory_catId(catId, pageable);

        return glossaryMapper.toDto(glossaryList);
    }

    @Override
    public List<GlossaryDto> getLimitedByCategoryIdWithoutSelf(int amount, final Long catId, List<Long> rightAnswerIds) {
        Pageable pageable = PageRequest.of(0, amount);
        List<Glossary> glossaryList = glossaryRepository.findNoRightAnswersByCategory_catId(catId, pageable, rightAnswerIds);

        return glossaryMapper.toDto(glossaryList);
    }

    @Override
    public GlossaryType saveGlossaryType(final GlossaryType glossaryType) {
        return glossaryTypeRepository.save(glossaryType);
    }

    @Override
    public List<GlossaryType> getGlossaryTypes() {
        return glossaryTypeRepository.findAll();
    }

    @Override
    public Glossary getEntityById(final Long glossaryId) {
        return glossaryRepository.getReferenceById(glossaryId);
    }
}
