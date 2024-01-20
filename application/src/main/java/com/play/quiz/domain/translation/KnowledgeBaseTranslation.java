package com.play.quiz.domain.translation;

import com.play.quiz.domain.Category;
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

@Entity
@Table(name = "Q_KNOWLEDGE_BASE_TRANSLATION")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeBaseTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "k_base_transl_generator")
    @SequenceGenerator(name = "k_base_transl_generator", sequenceName = "knowledge_base_transl_seq", allocationSize = 1)
    private Long translId;

    private String name;

    private String description;

    @OneToOne(targetEntity = Language.class)
    @JoinColumn(name = "LANG_ID")
    private Language language;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAT_ID")
    @ToString.Exclude
    private Category category;
}
