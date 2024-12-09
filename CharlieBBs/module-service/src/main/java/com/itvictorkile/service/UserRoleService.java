package com.itvictorkile.service;

import com.itvictorkile.dto.UserRoleDTO;

import java.util.List;

public interface UserRoleService {
    List<UserRoleDTO> getAllUserRoles();

    UserRoleDTO getUserRoleById(Long id);
    List<UserRoleDTO> getUserRoleByUserId(Long userId);

    UserRoleDTO createUserRole(UserRoleDTO userRoleDTO);

    UserRoleDTO updateUserRole(Long id, UserRoleDTO userRoleDTO);

    void deleteUserRole(Long id);
}
