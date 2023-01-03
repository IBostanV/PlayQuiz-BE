package com.play.quiz.fixtures;

import com.play.quiz.dto.RoleDto;
import com.play.quiz.enums.UserRole;

public class RoleDtoFixture {

    public static RoleDto getUserRole() {
        return RoleDto.builder()
                .roleId(UserRole.ROLE_USER.getRoleId())
                .name(UserRole.ROLE_USER)
                .build();
    }
}
