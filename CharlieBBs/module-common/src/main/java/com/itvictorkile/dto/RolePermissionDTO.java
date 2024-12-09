package com.itvictorkile.dto;

import com.itvictorkile.entity.Permission;
import com.itvictorkile.entity.Role;
import lombok.Data;

@Data
public class RolePermissionDTO {
    private Long id;
    private Role role;
    private Permission permission;
    // Getters and Setters
}