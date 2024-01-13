package com.project.shopapp.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleResponseDto {
    private String name;
    private Set<PermissionResponseDto> permissions;
}
