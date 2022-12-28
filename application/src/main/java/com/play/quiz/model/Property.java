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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Q_PROPERTIES")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prop_generator")
    @SequenceGenerator(name = "prop_generator", sequenceName = "prop_sequence", allocationSize = 1)
    private Long propertyId;

    private String name;

    private String value;

    @Column(name = "OLD_VALUE")
    private String oldValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Property that = (Property) o;
        return propertyId != null && Objects.equals(propertyId, that.propertyId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
