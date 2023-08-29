package com.play.quiz.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Q_GLOSSARY_TYPE")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GlossaryType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "glossary_type_generator")
    @SequenceGenerator(name = "glossary_type_generator", sequenceName = "glossary_type_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String options;

    private Boolean isActive;
}
