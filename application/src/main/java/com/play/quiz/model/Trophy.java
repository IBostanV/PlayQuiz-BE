package com.play.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Q_TROPHIES")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Trophy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trophy_generator")
    @SequenceGenerator(name = "trophy_generator", sequenceName = "trophies_seq", allocationSize = 1)
    private Long trophyId;

    private String name;

    @Lob
    private byte[] attachment;

    private String options;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Trophy trophy = (Trophy) o;
        return trophyId != null && Objects.equals(trophyId, trophy.trophyId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
