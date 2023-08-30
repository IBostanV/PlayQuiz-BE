package com.play.quiz.repository;

import com.play.quiz.domain.Trophy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrophyRepository extends JpaRepository<Trophy, Long> {
}
