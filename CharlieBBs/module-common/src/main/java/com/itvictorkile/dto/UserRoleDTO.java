package com.itvictorkile.dto;

import com.itvictorkile.entity.Role;
import com.itvictorkile.entity.User;
import lombok.Data;


@Data
public class UserRoleDTO {
    private long id;
    private User user;
    private Role role;
    // Getters and Setters
}