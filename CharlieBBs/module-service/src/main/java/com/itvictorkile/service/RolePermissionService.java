package com.itvictorkile.service;

import com.itvictorkile.dto.RolePermissionDTO;

import java.util.List;

public interface RolePermissionService {
    List<RolePermissionDTO> getAllRolePermissions();

    RolePermissionDTO getRolePermissionById(Long id);
    List<RolePermissionDTO> getRolePermissionByRoleId(Long role_id);

    RolePermissionDTO createRolePermission(RolePermissionDTO rolePermissionDTO);

    RolePermissionDTO updateRolePermission(Long id, RolePermissionDTO rolePermissionDTO);

    void deleteRolePermission(Long id);
}
