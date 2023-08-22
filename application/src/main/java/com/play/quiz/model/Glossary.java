package com.play.quiz.model;

import java.util.List;
import java.util.Objects;

import com.play.quiz.model.translation.GlossaryTranslation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

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

    @OneToOne(targetEntity = GlossaryType.class)
    @JoinColumn(name = "TYPE_ID")
    private GlossaryType type;

    @Lob
    private byte[] attachment;

    private String options;

    @OneToOne(targetEntity = Glossary.class)
    @JoinColumn(name = "PARENT_ID")
    private Glossary parent;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @OneToMany(mappedBy = "glossary",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    private List<GlossaryTranslation> glossaryTranslations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Glossary glossary = (Glossary) o;
        return termId != null && Objects.equals(termId, glossary.termId)
                && Objects.equals(key, glossary.key)
                && Objects.equals(category, glossary.category);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
