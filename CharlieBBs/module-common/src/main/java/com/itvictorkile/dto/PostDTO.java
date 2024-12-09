package com.itvictorkile.dto;

import lombok.Data;

@Data
public class PostDTO {
    private Long userId;
    private Long boardId;
    private String title;
    private String content;

    // Getters and Setters
}