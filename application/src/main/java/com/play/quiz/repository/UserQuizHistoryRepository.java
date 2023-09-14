package com.play.quiz.repository;

import com.play.quiz.domain.UserQuizHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuizHistoryRepository extends JpaRepository<UserQuizHistory, Long> {
}
