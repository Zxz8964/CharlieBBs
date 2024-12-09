package com.itvictorkile.api;

import com.itvictorkile.dto.UserDto;
import com.itvictorkile.entity.User;
import com.itvictorkile.service.UserService;
import com.itvictorkile.utils.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto userDto) {
        try {
            userService.registerUser(userDto);
            return ResponseEntity.ok("用户注册成功");
        } catch (IllegalArgumentException e) {
            // 这里的异常将会被 GlobalExceptionHandler 捕获
            throw e;
        } catch (Exception e) {
            // 其他异常也将会被 GlobalExceptionHandler 捕获
            throw new RuntimeException("注册失败", e);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserDto userDto) {
        try {
            Optional<User> user = userService.authenticateUser(userDto);
            if (user.isPresent()) {
                String token = tokenProvider.generateToken(user.get());
                return ResponseEntity.ok().body(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("凭据无效");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("登录失败: " + e.getMessage());
        }
    }
}
