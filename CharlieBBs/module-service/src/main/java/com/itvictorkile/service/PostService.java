package com.itvictorkile.service;

import com.itvictorkile.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post createPost(Post post);
    Post updatePost(Long id, Post post);
    void deletePost(Long id);
    Optional<Post> getPostById(Long id);
    List<Post> getAllPosts();
    Optional<Post> findById(Long id);
}