package com.itvictorkile.service.impl;

import com.itvictorkile.dao.UserRoleRepository;
import com.itvictorkile.dto.UserRoleDTO;
import com.itvictorkile.entity.UserRole;
import com.itvictorkile.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<UserRoleDTO> getAllUserRoles() {
        return userRoleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserRoleDTO getUserRoleById(Long id) {
        UserRole userRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserRole not found with id: " + id));
        return convertToDTO(userRole);
    }

    @Override
    public List<UserRoleDTO> getUserRoleByUserId(Long userId) {
        List<UserRole> userRoles = userRoleRepository.findByUserId(userId);
        if (userRoles.isEmpty()) {
            throw new IllegalArgumentException("No roles found for user with id: " + userId);
        }
        return userRoles.stream()
                .map(this::convertToDTO)  // 转换为 DTO
                .collect(Collectors.toList());
    }


    @Override
    public UserRoleDTO createUserRole(UserRoleDTO userRoleDTO) {
        // 检查是否存在同样的用户角色
        Optional<UserRole> existingUserRole = userRoleRepository.findByUserIdAndRoleId(userRoleDTO.getUser().getId(), userRoleDTO.getRole().getId());
        if (existingUserRole.isPresent()) {
            throw new IllegalArgumentException("UserRole with userId " + userRoleDTO.getUser().getId() + " and roleId " + userRoleDTO.getRole().getId() + " already exists.");
        }

        // 创建新的用户角色
        UserRole userRole = new UserRole();
        userRole.setUser(userRoleDTO.getUser());
        userRole.setRole(userRoleDTO.getRole());

        UserRole savedUserRole = userRoleRepository.save(userRole);
        return convertToDTO(savedUserRole);
    }

    @Override
    public UserRoleDTO updateUserRole(Long id, UserRoleDTO userRoleDTO) {
        UserRole userRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserRole not found with id: " + id));

        userRole.setUser(userRoleDTO.getUser());
        userRole.setRole(userRoleDTO.getRole());

        UserRole updatedUserRole = userRoleRepository.save(userRole);
        return convertToDTO(updatedUserRole);
    }

    @Override
    public void deleteUserRole(Long id) {
        if (!userRoleRepository.existsById(id)) {
            throw new IllegalArgumentException("UserRole not found with id: " + id);
        }
        userRoleRepository.deleteById(id);
    }

    private UserRoleDTO convertToDTO(UserRole userRole) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setId(userRole.getId());
        userRoleDTO.setUser(userRole.getUser());
        userRoleDTO.setRole(userRole.getRole());
        return userRoleDTO;
    }
}
