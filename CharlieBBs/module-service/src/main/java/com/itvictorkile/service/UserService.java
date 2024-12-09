package com.itvictorkile.service;

import com.itvictorkile.dto.UserDto;
import com.itvictorkile.entity.User;

import java.util.Optional;

public interface UserService {
    void registerUser(UserDto userDto);
    Optional<User> authenticateUser(UserDto userDto);
    Optional<User> getUserById(Long userId);

    Optional<User> findByUsername(String username);
}
