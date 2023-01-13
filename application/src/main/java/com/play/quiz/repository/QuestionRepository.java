package com.play.quiz.repository;

import com.play.quiz.enums.QuestionType;
import com.play.quiz.model.Category;
import com.play.quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByCategory_naturalId(final String naturalId);

    List<Question> findByTypeAndCategory(final QuestionType template, final Category category);

    @Query("UPDATE Question q SET q.isActive = CASE WHEN q.isActive = TRUE THEN FALSE ELSE TRUE END WHERE q.questionId = :questionId")
    void deactivate(final Long questionId);
}
