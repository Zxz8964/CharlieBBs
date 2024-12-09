package com.itvictorkile.dto;

import com.itvictorkile.entity.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Data
public class UserDto {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    private UserStatus status;
    private String avatarUrl;
    public UserDto() {}

    // getters and setters

    public UserDto(String username, String email, String password, Argon2PasswordEncoder passwordEncoder) {
        this.username = username;
        this.email = email;
        this.password = passwordEncoder.encode(password); // 进行密码哈希
        this.status = UserStatus.ACTIVE; // 设置为枚举常量
    }
}
