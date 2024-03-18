package com.play.quiz.repository;

import com.play.quiz.domain.UserOccupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOccupationRepository extends JpaRepository<UserOccupation, Long> {
}
