package com.play.quiz.repository;

import com.play.quiz.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySourceAndDestination(final String source, final String destination);
}
