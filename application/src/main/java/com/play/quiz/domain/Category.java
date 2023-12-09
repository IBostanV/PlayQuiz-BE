package com.play.quiz.domain;

import java.util.List;
import java.util.Objects;

import com.play.quiz.domain.translation.CategoryTranslation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "Q_CATEGORY")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
    @SequenceGenerator(name = "category_generator", sequenceName = "categories_seq", allocationSize = 1)
    private Long catId;
    private String name;
    private String naturalId;
    private Boolean visible;

    @OneToOne(targetEntity = Category.class)
    @JoinColumn(name = "SUBCATEGORY_ID")
    @ToString.Exclude
    private Category parent;

    @OneToMany(mappedBy = "category",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    private List<CategoryTranslation> categoryTranslations;

    public void setParent(Category parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Category category = (Category) o;
        return catId != null && Objects.equals(catId, category.catId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
