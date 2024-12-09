package com.itvictorkile.service;

import com.itvictorkile.dto.PostTagDTO;

import java.util.List;

public interface PostTagService {
    List<PostTagDTO> getAllPostTags();

    PostTagDTO getPostTagById(Long id);

    List<PostTagDTO> getPostTagsByPostId(Long postId);

    PostTagDTO createPostTag(PostTagDTO postTagDTO);

    PostTagDTO updatePostTag(Long id, PostTagDTO postTagDTO);

    void deletePostTag(Long id);
}
