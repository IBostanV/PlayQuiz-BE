package com.play.quiz.service;

import java.util.List;

import com.play.quiz.domain.Glossary;
import com.play.quiz.domain.GlossaryType;
import com.play.quiz.dto.GlossaryDto;
import org.springframework.web.multipart.MultipartFile;

public interface GlossaryService {

    GlossaryDto save(final GlossaryDto glossaryDto, final MultipartFile attachment);

    GlossaryDto getById(final Long glossaryId);

    GlossaryDto getByKey(final String glossaryKey);

    List<GlossaryDto> getByCategoryId(final Long categoryId);

    Integer toggleGlossary(final Long glossaryId);

    List<GlossaryDto> getLimitedByOptionWithoutSelf(int amount, String option, List<Long> rightAnswerIds);

    List<GlossaryDto> getLimitedByOptions(int amount, String... options);

    List<GlossaryDto> getLimitedByCategoryId(int amount, final Long catId);

    List<GlossaryDto> getLimitedByCategoryIdWithoutSelf(int amount, final Long catId, List<Long> rightAnswerIds);

    GlossaryType saveGlossaryType(final GlossaryType glossaryType);

    List<GlossaryType> getGlossaryTypes();

    Glossary getEntityById(final Long glossaryId);
}
