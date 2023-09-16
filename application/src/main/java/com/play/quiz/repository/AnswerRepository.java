package com.play.quiz.repository;

import java.util.List;

import com.play.quiz.domain.Answer;
import com.play.quiz.domain.GlossaryType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("SELECT a FROM Answer a WHERE a.glossary.type = :glossaryType AND a.ansId NOT IN :answerIdList")
    List<Answer> getByGlossaryTypeWithoutSelfWithLimit(final GlossaryType glossaryType, final List<Long> answerIdList, Pageable pageable);

    @Query("SELECT a FROM Answer a WHERE a.glossary.category.catId = :catId AND a.ansId NOT IN :answerIdList")
    List<Answer> getByCategoryIdWithoutSelfWithLimit(final Long catId, final List<Long> answerIdList, Pageable pageable);
}
