package com.itvictorkile.api;

import com.itvictorkile.dto.UserRoleDTO;
import com.itvictorkile.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userRoles")
public class UserRoleController {
    private final UserRoleService userRoleService;

    @Autowired
    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping
    public ResponseEntity<List<UserRoleDTO>> getAllUserRoles() {
        List<UserRoleDTO> userRoles = userRoleService.getAllUserRoles();
        return ResponseEntity.ok(userRoles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRoleDTO> getUserRoleById(@PathVariable Long id) {
        UserRoleDTO userRole = userRoleService.getUserRoleById(id);
        return ResponseEntity.ok(userRole);
    }

    @PostMapping
    public ResponseEntity<UserRoleDTO> createUserRole(@RequestBody UserRoleDTO userRoleDTO) {
        UserRoleDTO newUserRole = userRoleService.createUserRole(userRoleDTO);
        return ResponseEntity.ok(newUserRole);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRoleDTO> updateUserRole(@PathVariable Long id, @RequestBody UserRoleDTO userRoleDTO) {
        UserRoleDTO updatedUserRole = userRoleService.updateUserRole(id, userRoleDTO);
        return ResponseEntity.ok(updatedUserRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable Long id) {
        userRoleService.deleteUserRole(id);
        return ResponseEntity.noContent().build();
    }
}