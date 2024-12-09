package com.itvictorkile.dao;

import com.itvictorkile.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByUserIdAndRoleId(Long id, long id1);

    List<UserRole> findByUserId(Long userId);
}
