package com.itvictorkile.service.impl;

import com.itvictorkile.dao.RoleRepository;
import com.itvictorkile.dto.RoleDTO;
import com.itvictorkile.entity.Role;
import com.itvictorkile.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("role not found with id: " + id));
        return convertToDTO(role);
    }
    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
    //超级管理员新增角色api
    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        // 检查是否存在同名角色
        Optional<Role> existingRole = roleRepository.findByName(roleDTO.getName());
        if (existingRole.isPresent()) {
            throw new IllegalArgumentException("Role with name " + roleDTO.getName() + " already exists.");
        }

        // 创建新的角色
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        if (roleDTO.getParentId() != null) {
            Role parent = roleRepository.findById(roleDTO.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent role not found"));
            role.setParent(parent);
        }

        Role savedRole = roleRepository.save(role);
        return convertToDTO(savedRole);
    }

    @Override
    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("role not found with id: " + id));

        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        if (roleDTO.getParentId() != null) {
            Role parent = roleRepository.findById(roleDTO.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent board not found"));
            role.setParent(parent);
        } else {
            role.setParent(null);
        }

        Role updatedRole = roleRepository.save(role);
        return convertToDTO(updatedRole);
    }

    @Override
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new IllegalArgumentException("role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }

    private RoleDTO convertToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setDescription(role.getDescription());
        if (role.getParent() != null) {
            roleDTO.setParentId(role.getParent().getId());
        }
        return roleDTO;
    }
}