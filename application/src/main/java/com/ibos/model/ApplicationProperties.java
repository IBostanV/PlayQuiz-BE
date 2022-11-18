package com.ibos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Q_PROPERTY")
@Getter
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class ApplicationProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prop_generator")
    @SequenceGenerator(name = "prop_generator", sequenceName = "prop_sequence", allocationSize = 1)
    private Integer id;
    private String name;
    private String value;
    @Column(name = "OLD_VALUE")
    private String oldValue;
}
