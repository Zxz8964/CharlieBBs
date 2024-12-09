package com.itvictorkile.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long userId;
    private Long postId;
    private String content;
}