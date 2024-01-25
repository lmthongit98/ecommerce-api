package com.project.shopapp.dtos.responses;

import com.project.shopapp.enums.HttpMethods;
import com.project.shopapp.enums.Module;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionResponseDto {
    private String name;
    private String path;
    private HttpMethods method;
    private Module module;
}
