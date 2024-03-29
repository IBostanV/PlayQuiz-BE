package com.play.quiz.repository;

import java.util.List;
import java.util.Set;

import com.play.quiz.domain.Category;
import com.play.quiz.domain.Question;
import com.play.quiz.enums.QuestionType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByCategory_naturalId(final String naturalId);

    List<Question> findByTypeAndCategory(final QuestionType template, final Category category);

    @Modifying
    @Query("UPDATE Question q SET q.isActive = CASE WHEN q.isActive = TRUE THEN FALSE ELSE TRUE END WHERE q.questionId = :questionId")
    void deactivate(final Long questionId);

    List<Question> findAllByQuestionIdIn(final Set<Long> idList);

    List<Question> getByCategory_catId(Long catId, Pageable pageable);
}
