package com.play.quiz.domain;

import com.play.quiz.enums.KnowledgeBaseRecordStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "Q_KNOWLEDGE_BASE")
@Getter
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeBaseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "knowledge_base_generator")
    @SequenceGenerator(name = "knowledge_base_generator", sequenceName = "knowledge_base_seq", allocationSize = 1)
    private Long id;
    private byte[] attachment;
    private String content;
    private Integer downvotes;
    private String tags;
    private String title;
    private Integer upvotes;
    private Boolean visible;
    @Enumerated(EnumType.STRING)
    private KnowledgeBaseRecordStatus status;

    @OneToOne(targetEntity = Category.class)
    @JoinColumn(name = "CAT_ID")
    private Category category;

    @Setter
    @OneToOne(targetEntity = KnowledgeBaseRecord.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    @ToString.Exclude
    private KnowledgeBaseRecord parent;
}
