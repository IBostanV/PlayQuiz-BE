package com.play.quiz.domain.translation;

import java.util.Objects;

import com.play.quiz.domain.Glossary;
import com.play.quiz.domain.Language;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Table(name = "Q_GLOSSARY_TRANSLATIONS")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GlossaryTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "term_transl_generator")
    @SequenceGenerator(name = "term_transl_generator", sequenceName = "term_transl_seq", allocationSize = 1)
    private Long translId;
    private String name;
    private String description;

    @OneToOne(targetEntity = Language.class)
    @JoinColumn(name = "LANG_ID")
    private Language language;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TERM_ID")
    @ToString.Exclude
    private Glossary glossary;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        GlossaryTranslation that = (GlossaryTranslation) o;
        return getTranslId() != null && Objects.equals(getTranslId(), that.getTranslId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
