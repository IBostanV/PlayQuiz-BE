package com.play.quiz.dto;

import com.play.quiz.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoleDto {

   Long roleId;

   @NotNull UserRole name;
}
