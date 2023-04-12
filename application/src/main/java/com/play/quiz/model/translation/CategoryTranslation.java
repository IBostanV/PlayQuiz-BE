package com.play.quiz.model.translation;

import com.play.quiz.model.Category;
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
@Table(name = "Q_CATEGORY_TRANSLATIONS")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CategoryTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cat_transl_generator")
    @SequenceGenerator(name = "cat_transl_generator", sequenceName = "cat_transl_seq", allocationSize = 1)
    private Long transId;

    private String name;

    private String description;

    @OneToOne(targetEntity = Language.class)
    @JoinColumn(name = "LANG_ID")
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAT_ID")
    private Category category;
}
