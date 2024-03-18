package com.play.quiz.mapper;

import com.play.quiz.domain.UserOccupation;
import com.play.quiz.dto.UserOccupationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserOccupationMapper {

    @Mapping(source = "parentId", target = "parent.id")
    UserOccupation toEntity(final UserOccupationDto userOccupationDto);

    @Mapping(source = "parent.id", target = "parentId")
    UserOccupationDto toDto(final UserOccupation userOccupation);

    List<UserOccupationDto> toDtoList(List<UserOccupation> userOccupationList);

    List<UserOccupation> toEntityList(List<UserOccupationDto> userOccupationDtoList);
}
