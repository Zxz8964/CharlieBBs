package com.itvictorkile.dto;

import com.itvictorkile.entity.Post;
import com.itvictorkile.entity.Tags;
import lombok.Data;

@Data
public class PostTagDTO {
    private Long id;
    private Post post;
    private Tags tag;
}
