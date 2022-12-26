package com.play.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Q_GLOSSARIES")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Glossary {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "glossary_generator")
    @SequenceGenerator(name = "glossary_generator", sequenceName = "glossaries_seq", allocationSize = 1)
    private Long termId;

    private String key;

    private String value;

    @OneToOne(targetEntity = Category.class)
    @JoinColumn(name = "CAT_ID")
    private Category category;

    @Lob
    private byte[] attachment;

    private String options;

    @OneToOne(targetEntity = Glossary.class)
    @JoinColumn(name = "PARENT_ID")
    private Glossary parent;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Glossary glossary = (Glossary) o;
        return termId != null && Objects.equals(termId, glossary.termId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
