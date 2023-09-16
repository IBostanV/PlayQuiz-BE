package com.play.quiz.dto;

import com.play.quiz.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
   private Long roleId;

   @NotNull
   private UserRole name;
}
