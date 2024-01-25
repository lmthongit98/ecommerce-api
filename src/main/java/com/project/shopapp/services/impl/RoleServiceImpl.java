package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.responses.PermissionResponseDto;
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
        List<Permission> permissions = permissionRepository.findAll();
        return roles.stream().map(p -> roleMapper.mapToDto(p, permissions)).collect(Collectors.toList());
    }

    @Override
    public RoleResponseDto getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", id));
        List<Permission> permissions = permissionRepository.findAll();
        return roleMapper.mapToDto(role, permissions);
    }

}
