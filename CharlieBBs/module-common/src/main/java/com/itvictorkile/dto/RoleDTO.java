package com.itvictorkile.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleDTO {
    private Long id;
    @NotBlank(message = "Username cannot be blank")
    private String name;
    private String description;
    private Long parentId;

    // Getters and Setters
}