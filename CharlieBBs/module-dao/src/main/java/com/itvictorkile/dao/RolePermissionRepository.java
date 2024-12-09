package com.itvictorkile.dao;

import com.itvictorkile.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    Optional<RolePermission> findByRoleIdAndPermissionId(long id, long id1);

    List<RolePermission> findByRoleId(Long roleId);
}
