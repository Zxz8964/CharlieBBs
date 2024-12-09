package com.itvictorkile.service.impl;

import com.itvictorkile.dao.RolePermissionRepository;
import com.itvictorkile.dto.RolePermissionDTO;
import com.itvictorkile.entity.RolePermission;
import com.itvictorkile.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;

    @Autowired
    public RolePermissionServiceImpl(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public List<RolePermissionDTO> getAllRolePermissions() {
        return rolePermissionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RolePermissionDTO getRolePermissionById(Long id) {
        RolePermission rolePermission = rolePermissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RolePermission not found with id: " + id));
        return convertToDTO(rolePermission);
    }

    @Override
    public List<RolePermissionDTO> getRolePermissionByRoleId(Long role_id) {
        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(role_id);
        if (rolePermissions.isEmpty()) {
            throw new IllegalArgumentException("No permission found for role with id: " + role_id);
        }
        return rolePermissions.stream()
                .map(this::convertToDTO)  // 转换为 DTO
                .collect(Collectors.toList());
    }


    @Override
    public RolePermissionDTO createRolePermission(RolePermissionDTO rolePermissionDTO) {
        // 检查是否存在同样的角色权限
        Optional<RolePermission> existingRolePermission = rolePermissionRepository.findByRoleIdAndPermissionId(rolePermissionDTO.getRole().getId(), rolePermissionDTO.getPermission().getId());
        if (existingRolePermission.isPresent()) {
            throw new IllegalArgumentException("RolePermission with roleId " + rolePermissionDTO.getRole().getId() + " and permissionId " + rolePermissionDTO.getPermission().getId() + " already exists.");
        }

        // 创建新的角色权限
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole(rolePermissionDTO.getRole());
        rolePermission.setPermission(rolePermissionDTO.getPermission());

        RolePermission savedRolePermission = rolePermissionRepository.save(rolePermission);
        return convertToDTO(savedRolePermission);
    }

    @Override
    public RolePermissionDTO updateRolePermission(Long id, RolePermissionDTO rolePermissionDTO) {
        RolePermission rolePermission = rolePermissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RolePermission not found with id: " + id));

        rolePermission.setRole(rolePermissionDTO.getRole());
        rolePermission.setPermission(rolePermissionDTO.getPermission());

        RolePermission updatedRolePermission = rolePermissionRepository.save(rolePermission);
        return convertToDTO(updatedRolePermission);
    }

    @Override
    public void deleteRolePermission(Long id) {
        if (!rolePermissionRepository.existsById(id)) {
            throw new IllegalArgumentException("RolePermission not found with id: " + id);
        }
        rolePermissionRepository.deleteById(id);
    }

    private RolePermissionDTO convertToDTO(RolePermission rolePermission) {
        RolePermissionDTO rolePermissionDTO = new RolePermissionDTO();
        rolePermissionDTO.setId(rolePermission.getId());
        rolePermissionDTO.setRole(rolePermission.getRole());
        rolePermissionDTO.setPermission(rolePermission.getPermission());
        return rolePermissionDTO;
    }
}
