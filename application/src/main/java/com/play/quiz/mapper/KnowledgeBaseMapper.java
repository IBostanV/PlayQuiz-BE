package com.play.quiz.mapper;

import java.util.List;
import java.util.Objects;

import com.play.quiz.domain.KnowledgeBaseRecord;
import com.play.quiz.dto.KnowledgeBaseRecordDto;
import lombok.SneakyThrows;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public interface KnowledgeBaseMapper {

    @Mapping(source = "category.catId", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "parent.id", target = "parentId")
    KnowledgeBaseRecordDto toDto(KnowledgeBaseRecord knowledgeBaseRecord);

    @Mapping(source = "categoryId", target = "category.catId")
    @Mapping(source = "categoryName", target = "category.name")
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "handleParent")
    @Mapping(target = "attachment", expression = "java(handleAttachment(attachment))")
    KnowledgeBaseRecord toEntity(final KnowledgeBaseRecordDto recordDto, @Context MultipartFile attachment);

    @Named("handleParent")
    default KnowledgeBaseRecord handleParent(final Long id) {
        if (Objects.nonNull(id)) {
            return KnowledgeBaseRecord.builder().id(id).build();
        }
        return null;
    }

    @SneakyThrows
    @Named("handleAttachment")
    default byte[] handleAttachment(final MultipartFile attachment) {
        return Objects.nonNull(attachment) ? attachment.getBytes() : null;
    }

    List<KnowledgeBaseRecordDto> toDto(List<KnowledgeBaseRecord> knowledgeBaseRecords);
}
