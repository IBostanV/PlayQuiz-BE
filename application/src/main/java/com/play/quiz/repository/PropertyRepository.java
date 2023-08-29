package com.play.quiz.repository;

import com.play.quiz.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    Property findByName(final String name);
}
