package com.project.shopapp.controllers;

import com.project.shopapp.dtos.responses.RoleResponseDto;
import com.project.shopapp.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;


    @GetMapping
    public ResponseEntity<?> getRoles() {
        List<RoleResponseDto> roles = roleService.getRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        RoleResponseDto role = roleService.getRoleById(id);
        return ResponseEntity.ok(role);
    }

}
