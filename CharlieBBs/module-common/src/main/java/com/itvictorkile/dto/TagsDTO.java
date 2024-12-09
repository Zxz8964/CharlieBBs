package com.itvictorkile.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagsDTO {
    private Long id;
    @NotBlank(message = "tagname cannot be blank")
    private String name;
    private String description;
}
