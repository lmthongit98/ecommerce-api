package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.requests.RoleRequestDto;
import com.project.shopapp.dtos.responses.RoleResponseDto;
import com.project.shopapp.exceptions.ResourceNotFoundException;
import com.project.shopapp.mappers.RoleMapper;
import com.project.shopapp.models.Permission;
import com.project.shopapp.models.Role;
import com.project.shopapp.repositories.PermissionRepository;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleResponseDto> getRoles() {
        List<Role> roles = roleRepository.findAll();
        List<Permission> permissions = getAllPermissions();
        return roles.stream().map(p -> roleMapper.mapToDto(p, permissions)).collect(Collectors.toList());
    }

    @Override
    public RoleResponseDto getRoleById(Long id) {
        Role role = getRole(id);
        List<Permission> permissions = getAllPermissions();
        return roleMapper.mapToDto(role, permissions);
    }

    @Override
    public RoleResponseDto updateRoleById(Long id, RoleRequestDto roleRequestDto) {
        Role role = getRole(id);
        role.setName(roleRequestDto.getName());
        role.setDescription(roleRequestDto.getDescription());
        role.setActive(roleRequestDto.isActive());
        updateRolePermissions(roleRequestDto, role);
        Role updatedRole = roleRepository.save(role);
        return roleMapper.mapToDto(updatedRole, getAllPermissions());
    }

    @Override
    public RoleResponseDto createRole(RoleRequestDto roleRequestDto) {
        Role role = roleMapper.mapToEntity(roleRequestDto, getAllPermissions());
        return roleMapper.mapToDto(roleRepository.save(role), getAllPermissions());
    }

    @Override
    public void deleteRoleById(Long id) {
        Role role = getRole(id);
        roleRepository.delete(role);
    }

    private void updateRolePermissions(RoleRequestDto roleRequestDto, Role role) {
        for (var permissionRequestDto : roleRequestDto.getPermissions()) {
            Permission permission = permissionRepository.findById(permissionRequestDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Permission", permissionRequestDto.getId()));
            if (permissionRequestDto.isEnabled()) {
                role.addPermission(permission);
                continue;
            }
            role.removePermission(permission);
        }
    }

    private Role getRole(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", id));
    }

    private List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }


}
