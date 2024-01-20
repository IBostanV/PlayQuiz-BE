package com.play.quiz.mapper;

import com.play.quiz.domain.GlossaryType;
import com.play.quiz.dto.GlossaryTypeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GlossaryTypeMapper {

    GlossaryTypeDto toDto(final GlossaryType glossary);

    List<GlossaryTypeDto> toDtoList(final List<GlossaryType> glossaryTypeList);
}
