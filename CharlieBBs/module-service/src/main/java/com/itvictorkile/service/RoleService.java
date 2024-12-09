package com.itvictorkile.service;

import com.itvictorkile.dto.RoleDTO;
import com.itvictorkile.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleDTO> getAllRoles();

    RoleDTO getRoleById(Long id);

    RoleDTO createRole(RoleDTO roleDTO);

    RoleDTO updateRole(Long id, RoleDTO roleDTO);

    void deleteRole(Long id);
    Optional<Role> findByName(String name);
}