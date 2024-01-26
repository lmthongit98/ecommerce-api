package com.project.shopapp.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionRequestDto {
    private Long id;
    private boolean enabled;
}
