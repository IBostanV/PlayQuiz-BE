package com.play.quiz.repository;

import com.play.quiz.domain.KnowledgeBaseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeBaseRepository extends JpaRepository<KnowledgeBaseRecord, Long> {
}
