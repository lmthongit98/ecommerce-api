package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.RoleRequestDto;
import com.project.shopapp.dtos.responses.PermissionResponseDto;
import com.project.shopapp.dtos.responses.RoleResponseDto;
import com.project.shopapp.enums.Module;
import com.project.shopapp.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getRoles() {
        var roleResponseDtoList = roleService.getRoles();
        return ResponseEntity.ok(roleResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Long id) {
        var roleResponseDto = roleService.getRoleById(id);
        return ResponseEntity.ok(roleResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDto> updateRoleById(@PathVariable Long id, @RequestBody RoleRequestDto roleRequestDto) {
        var roleResponseDto = roleService.updateRoleById(id, roleRequestDto);
        return ResponseEntity.ok(roleResponseDto);
    }

    @PostMapping
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleRequestDto roleRequestDto) {
        var roleResponseDto = roleService.createRole(roleRequestDto);
        return ResponseEntity.ok(roleResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable Long id) {
        roleService.deleteRoleById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/permissions")
    public ResponseEntity<?> getAllPermissions() {
        var modulePermissionMap = roleService.getModulePermissionMap();
        return ResponseEntity.ok(modulePermissionMap);
    }

}
