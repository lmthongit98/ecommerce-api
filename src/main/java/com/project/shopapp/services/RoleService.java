package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.RoleRequestDto;
import com.project.shopapp.dtos.responses.RoleResponseDto;

import java.util.List;

public interface RoleService {
    List<RoleResponseDto> getRoles();

    RoleResponseDto getRoleById(Long id);

    RoleResponseDto updateRoleById(Long id, RoleRequestDto roleRequestDto);

    RoleResponseDto createRole(RoleRequestDto roleRequestDto);

    void deleteRoleById(Long id);
}
