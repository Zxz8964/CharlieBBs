package com.itvictorkile.api;

import com.itvictorkile.dto.RolePermissionDTO;
import com.itvictorkile.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rolePermissions")
public class RolePermissionController {
    private final RolePermissionService rolePermissionService;

    @Autowired
    public RolePermissionController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @GetMapping
    public ResponseEntity<List<RolePermissionDTO>> getAllRolePermissions() {
        List<RolePermissionDTO> rolePermissions = rolePermissionService.getAllRolePermissions();
        return ResponseEntity.ok(rolePermissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolePermissionDTO> getRolePermissionById(@PathVariable Long id) {
        RolePermissionDTO rolePermission = rolePermissionService.getRolePermissionById(id);
        return ResponseEntity.ok(rolePermission);
    }

    @PostMapping
    public ResponseEntity<RolePermissionDTO> createRolePermission(@RequestBody RolePermissionDTO rolePermissionDTO) {
        RolePermissionDTO newRolePermission = rolePermissionService.createRolePermission(rolePermissionDTO);
        return ResponseEntity.ok(newRolePermission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolePermissionDTO> updateRolePermission(@PathVariable Long id, @RequestBody RolePermissionDTO rolePermissionDTO) {
        RolePermissionDTO updatedRolePermission = rolePermissionService.updateRolePermission(id, rolePermissionDTO);
        return ResponseEntity.ok(updatedRolePermission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRolePermission(@PathVariable Long id) {
        rolePermissionService.deleteRolePermission(id);
        return ResponseEntity.noContent().build();
    }
}
