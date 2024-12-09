package com.itvictorkile.service;

import com.itvictorkile.dto.PermissionDTO;
import com.itvictorkile.entity.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    List<PermissionDTO> getAllPermissions();

    PermissionDTO getPermissionById(Long id);

    Optional<Permission> findByName(String name);

    PermissionDTO createPermission(PermissionDTO permissionDTO);

    PermissionDTO updatePermission(Long id, PermissionDTO permissionDTO);

    void deletePermission(Long id);
}