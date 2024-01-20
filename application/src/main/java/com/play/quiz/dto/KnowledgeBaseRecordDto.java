package com.play.quiz.dto;

import com.play.quiz.enums.KnowledgeBaseRecordStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeBaseRecordDto {
    private Long id;
    private byte[] attachment;
    private Integer categoryId;
    private String categoryName;
    private String content;
    private Integer downvotes;
    private Integer parentId;
    private String tags;
    private String title;
    private Integer upvotes;
    private Boolean visible;
    private KnowledgeBaseRecordStatus status;
}
