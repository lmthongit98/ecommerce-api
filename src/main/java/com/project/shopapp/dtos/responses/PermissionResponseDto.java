package com.project.shopapp.dtos.responses;

import com.project.shopapp.enums.HttpMethods;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionResponseDto {
    private String name;
    private String path;
    private HttpMethods method;
}
