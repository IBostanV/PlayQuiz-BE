package com.ibos.repository;

import com.ibos.enums.QuestionCategory;
import com.ibos.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(QuestionCategory category);
}
