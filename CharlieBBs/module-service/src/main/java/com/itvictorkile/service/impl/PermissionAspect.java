package com.itvictorkile.service.impl;

import com.itvictorkile.annotations.RequiresPermission;
import com.itvictorkile.dto.RolePermissionDTO;
import com.itvictorkile.dto.UserRoleDTO;
import com.itvictorkile.entity.Role;
import com.itvictorkile.entity.User;
import com.itvictorkile.service.RolePermissionService;
import com.itvictorkile.service.UserRoleService;
import com.itvictorkile.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class PermissionAspect {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RolePermissionService rolePermissionService;

    @Autowired
    public PermissionAspect(UserService userService, UserRoleService userRoleService, RolePermissionService rolePermissionService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.rolePermissionService = rolePermissionService;
    }

    @Pointcut("@annotation(com.itvictorkile.annotations.RequiresPermission)")
    public void permissionCheck() {}

    @Around("permissionCheck() && @annotation(requiresPermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint, RequiresPermission requiresPermission) throws Throwable {
        String permission = requiresPermission.value();
        String username = getCurrentUsername();

        System.out.println("==== Permission Check ====");
        System.out.println("Checking permission for user: " + username);
        System.out.println("Required permission: " + permission);

        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isEmpty()) {
            System.out.println("User not found: " + username);
            throw new AccessDeniedException("没有权限访问");
        }

        User user = userOptional.get();
        System.out.println("User found: " + user.getUsername() + " with ID: " + user.getId());

        List<UserRoleDTO> roles = userRoleService.getUserRoleByUserId(user.getId());
        System.out.println("Checking roles for user: " + user.getUsername());
        System.out.println("Roles found: " + roles.size());

        // 输出用户的角色和对应的权限
        for (UserRoleDTO userRole : roles) {
            Role role = userRole.getRole(); // 获取角色对象
            System.out.println("Role: " + role.getName()); // 输出角色名称
            List<RolePermissionDTO> permissions = rolePermissionService.getRolePermissionByRoleId(role.getId());
            System.out.println("Permissions for role " + role.getName() + ":");
            for (RolePermissionDTO rolePermission : permissions) {
                System.out.println(" - " + rolePermission.getPermission().getName());
            }
        }

        boolean hasPermission = hasPermission(user, permission);
        System.out.println("User " + user.getUsername() + (hasPermission ? " has" : " does not have") + " the permission: " + permission);

        if (!hasPermission) {
            System.out.println("User " + user.getUsername() + " does not have permission: " + permission);
            throw new AccessDeniedException("没有权限访问");
        }

        System.out.println("User " + user.getUsername() + " has permission: " + permission);
        System.out.println("Proceeding with method: " + joinPoint.getSignature().getName());
        return joinPoint.proceed();
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null; // 未登录用户
    }

    private boolean hasPermission(User user, String permission) {
        List<UserRoleDTO> roles = userRoleService.getUserRoleByUserId(user.getId());

        // 检查用户是否具有所需权限
        return roles.stream()
                .flatMap(userRole -> rolePermissionService.getRolePermissionByRoleId(userRole.getRole().getId()).stream())
                .anyMatch(p -> p.getPermission().getName().equals(permission));
    }
}