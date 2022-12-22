package com.play.quiz.repository;

import com.play.quiz.model.Glossary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlossaryRepository extends JpaRepository<Glossary, Long> {
}
