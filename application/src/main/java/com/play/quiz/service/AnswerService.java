package com.play.quiz.service;

import java.util.List;

import com.play.quiz.domain.Answer;
import com.play.quiz.domain.GlossaryType;

public interface AnswerService {

    List<Answer> getWrongOptionsByGlossaryTypeWithLimit(final GlossaryType glossaryType, final List<Long> answerIdList, int limit);

    List<Answer> getWrongOptionsByCategoryIdWithLimit(final Long catId, final List<Long> answerIdList, int limit);
}
