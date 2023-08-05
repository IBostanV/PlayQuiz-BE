package com.play.quiz.model.translation;

import com.play.quiz.model.Answer;
import com.play.quiz.model.Language;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Q_ANSWER_TRANSLATIONS")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AnswerTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_transl_generator")
    @SequenceGenerator(name = "answer_transl_generator", sequenceName = "answer_transl_seq", allocationSize = 1)
    private Long translId;

    private String name;

    private String description;

    @OneToOne(targetEntity = Language.class)
    @JoinColumn(name = "LANG_ID")
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANS_ID")
    private Answer answer;
}
