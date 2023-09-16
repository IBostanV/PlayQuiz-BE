package com.play.quiz.service.impl;

import java.util.List;

import com.play.quiz.domain.Answer;
import com.play.quiz.domain.GlossaryType;
import com.play.quiz.repository.AnswerRepository;
import com.play.quiz.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    @Override
    public List<Answer> getWrongOptionsByGlossaryTypeWithLimit(final GlossaryType glossaryType, final List<Long> answerIdList, int amount) {
        Pageable pageable = PageRequest.of(0, amount);
        return answerRepository.getByGlossaryTypeWithoutSelfWithLimit(glossaryType, answerIdList, pageable);
    }

    @Override
    public List<Answer> getWrongOptionsByCategoryIdWithLimit(final Long catId, final List<Long> answerIdList, int amount) {
        Pageable pageable = PageRequest.of(0, amount);
        return answerRepository.getByCategoryIdWithoutSelfWithLimit(catId, answerIdList, pageable);
    }
}
