package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.responses.RoleResponseDto;
import com.project.shopapp.exceptions.ResourceNotFoundException;
import com.project.shopapp.mappers.RoleMapper;
import com.project.shopapp.models.Role;
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
    private final RoleMapper roleMapper;

    @Override
    public List<RoleResponseDto> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public RoleResponseDto getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", id));
        return roleMapper.mapToDto(role);
    }

}
