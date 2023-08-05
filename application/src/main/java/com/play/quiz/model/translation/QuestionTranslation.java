package com.play.quiz.model.translation;

import java.util.Objects;

import com.play.quiz.model.Language;
import com.play.quiz.model.Question;
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
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Table(name = "Q_QUESTION_TRANSLATIONS")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_transl_generator")
    @SequenceGenerator(name = "question_transl_generator", sequenceName = "question_transl_seq", allocationSize = 1)
    private Long translId;

    private String name;

    private String description;

    @OneToOne(targetEntity = Language.class)
    @JoinColumn(name = "LANG_ID")
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID")
    @ToString.Exclude
    private Question question;

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        QuestionTranslation that = (QuestionTranslation) o;
        return getTranslId() != null && Objects.equals(getTranslId(), that.getTranslId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
