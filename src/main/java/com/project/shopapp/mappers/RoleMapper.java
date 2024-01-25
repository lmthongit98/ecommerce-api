package com.project.shopapp.mappers;

import com.project.shopapp.dtos.responses.PermissionResponseDto;
import com.project.shopapp.dtos.responses.RoleResponseDto;
import com.project.shopapp.enums.Module;
import com.project.shopapp.models.Permission;
import com.project.shopapp.models.Role;
import com.project.shopapp.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final ObjectMapperUtils objectMapperUtils;

    public RoleResponseDto mapToDto(Role role, List<Permission> permissions) {
        RoleResponseDto roleResponseDto = objectMapperUtils.mapToEntityOrDto(role, RoleResponseDto.class);
        List<PermissionResponseDto> permissionsResponse = objectMapperUtils.mapAll(role.getPermissions(), PermissionResponseDto.class);
        addDisablePermissions(permissions, permissionsResponse);
        Map<Module, List<PermissionResponseDto>> groupedPermissions = permissionsResponse.stream().collect(Collectors.groupingBy(PermissionResponseDto::getModule));
        roleResponseDto.setGroupedPermissions(groupedPermissions);
        return roleResponseDto;
    }

    private void addDisablePermissions(List<Permission> permissions, List<PermissionResponseDto> permissionsResponse) {
        for (Permission permission : permissions) {
            if (permissionsResponse.stream().noneMatch(p -> p.getId().equals(permission.getId()))) {
                var permissionResponse = objectMapperUtils.mapToEntityOrDto(permission, PermissionResponseDto.class);
                permissionResponse.setEnabled(false);
                permissionsResponse.add(permissionResponse);
            }
        }
    }

}
