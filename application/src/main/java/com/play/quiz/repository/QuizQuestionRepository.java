package com.play.quiz.repository;

import com.play.quiz.enums.QuestionCategory;
import com.play.quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(QuestionCategory category);
}
