package com.play.quiz.repository;

import com.play.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Quiz findFirstByCategory_CatId(final Long categoryId);
}
