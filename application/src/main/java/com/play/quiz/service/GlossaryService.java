package com.play.quiz.service;

import com.play.quiz.dto.GlossaryDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GlossaryService {

    GlossaryDto save(final GlossaryDto glossaryDto, final MultipartFile attachment);

    GlossaryDto getById(final Long glossaryId);

    GlossaryDto getByKey(final String glossaryKey);

    GlossaryDto getByCategoryId(final Long categoryId);

    void toggleGlossary(final Long glossaryId);
}
