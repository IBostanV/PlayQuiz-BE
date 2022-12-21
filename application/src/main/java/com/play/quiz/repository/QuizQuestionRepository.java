package com.play.quiz.repository;

import com.play.quiz.model.Category;
import com.play.quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(final Category category);
}
