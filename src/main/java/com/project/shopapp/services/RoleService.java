package com.project.shopapp.services;

import com.project.shopapp.dtos.responses.RoleResponseDto;

import java.util.List;

public interface RoleService {
    List<RoleResponseDto> getRoles();

    RoleResponseDto getRoleById(Long id);
}
