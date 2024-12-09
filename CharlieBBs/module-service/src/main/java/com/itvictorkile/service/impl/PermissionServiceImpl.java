package com.itvictorkile.service.impl;

import com.itvictorkile.dao.PermissionRepository;
import com.itvictorkile.dto.PermissionDTO;
import com.itvictorkile.entity.Permission;
import com.itvictorkile.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }


    @Override
    public List<PermissionDTO> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PermissionDTO getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("permission not found with id: " + id));
        return convertToDTO(permission);
    }
    @Override
    public Optional<Permission> findByName(String name) {
        return permissionRepository.findByName(name);
    }
    @Override
    public PermissionDTO createPermission(PermissionDTO permissionDTO) {
        // 检查是否存在同名权限
        Optional<Permission> existingRole = permissionRepository.findByName(permissionDTO.getName());
        if (existingRole.isPresent()) {
            throw new IllegalArgumentException("Permission with name " + permissionDTO.getName() + " already exists.");
        }

        // 创建新的角色
        Permission permission = new Permission();
        permission.setName(permissionDTO.getName());
        permission.setDescription(permissionDTO.getDescription());
        Permission savedPermission = permissionRepository.save(permission);
        return convertToDTO(savedPermission);
    }

    @Override
    public PermissionDTO updatePermission(Long id, PermissionDTO permissionDTO) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("permission not found with id: " + id));

        permission.setName(permissionDTO.getName());
        permission.setDescription(permissionDTO.getDescription());
        Permission updatedRole = permissionRepository.save(permission);
        return convertToDTO(updatedRole);
    }

    @Override
    public void deletePermission(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new IllegalArgumentException("permission not found with id: " + id);
        }
        permissionRepository.deleteById(id);
    }

    private PermissionDTO convertToDTO(Permission permission) {
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setId(permission.getId());
        permissionDTO.setName(permission.getName());
        permissionDTO.setDescription(permission.getDescription());
        return permissionDTO;
    }
}