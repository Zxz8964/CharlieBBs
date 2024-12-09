package com.itvictorkile.service.impl;

import com.itvictorkile.dao.UserRepository;
import com.itvictorkile.dto.UserDto;
import com.itvictorkile.entity.User;
import com.itvictorkile.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Argon2PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Argon2PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void registerUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userDto.getEmail());
        }

        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + userDto.getUsername());
        }
        // 使用构造函数创建 UserDto 对象
        UserDto newUserDto = new UserDto(userDto.getUsername(), userDto.getEmail(), userDto.getPassword(), passwordEncoder);

        User user = new User();
        user.setUsername(newUserDto.getUsername());
        user.setEmail(newUserDto.getEmail());
        user.setPasswordHash(newUserDto.getPassword()); // 已经在DTO中加密
        user.setStatus(newUserDto.getStatus());
        user.setAvatarUrl(userDto.getAvatarUrl());
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("由于数据库错误，注册失败", e);
        }
    }

    @Override
    public Optional<User> authenticateUser(UserDto userDto) {
        // 先尝试通过邮箱进行认证
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());

        if (user.isEmpty()) {
            // 如果邮箱认证失败，尝试通过用户名进行认证
            user = userRepository.findByUsername(userDto.getUsername());
        }

        return user.filter(userObj -> passwordEncoder.matches(userDto.getPassword(), userObj.getPasswordHash()));
    }
    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
