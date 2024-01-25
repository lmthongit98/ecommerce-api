package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.responses.PermissionResponseDto;
import com.project.shopapp.dtos.responses.RoleResponseDto;
import com.project.shopapp.enums.Module;
import com.project.shopapp.models.Role;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.services.RoleService;
import com.project.shopapp.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ObjectMapperUtils objectMapperUtils;

    @Override
    public List<RoleResponseDto> getRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResponseDto> roleResponseDtoList = new ArrayList<>();
        for (Role role : roles) {
            RoleResponseDto roleResponseDto = objectMapperUtils.mapToEntityOrDto(role, RoleResponseDto.class);
            List<PermissionResponseDto> permissionsResponse = objectMapperUtils.mapAll(role.getPermissions(), PermissionResponseDto.class);
            Map<Module, List<PermissionResponseDto>> groupedPermissions = permissionsResponse.stream().collect(Collectors.groupingBy(PermissionResponseDto::getModule));
            roleResponseDto.setGroupedPermissions(groupedPermissions);
            roleResponseDtoList.add(roleResponseDto);
        }
        return roleResponseDtoList;
    }
}
