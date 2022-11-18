package com.ibos.model;

import com.ibos.enums.QuestionCategory;
import com.ibos.enums.QuestionTopic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "Q_QUESTION")
@Getter
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qstn_generator")
    @SequenceGenerator(name = "qstn_generator", sequenceName = "qstn_sequence", allocationSize = 1)
    private Integer id;
    private String content;
    private int priority;
    @Column(name = "DIFFICULTY")
    private int difficulty;
    @Enumerated
    private QuestionTopic topic;
    @Enumerated
    private QuestionCategory category;
    @Transient
    private List<String> answers;
}
