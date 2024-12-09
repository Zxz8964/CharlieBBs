package com.itvictorkile.service.impl;

import com.itvictorkile.dao.BoardRepository;
import com.itvictorkile.dao.PostRepository;
import com.itvictorkile.dao.UserRepository;
import com.itvictorkile.entity.Post;
import com.itvictorkile.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Post createPost(Post post) {
        validatePost(post);
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, Post post) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("Post not found with id: " + id);
        }
        validatePost(post);
        post.setId(id);
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    private void validatePost(Post post) {
        if (post.getBoard() == null || post.getBoard().getId() == null) {
            throw new IllegalArgumentException("Board cannot be null");
        }
        if (!boardRepository.existsById(post.getBoard().getId())) {
            throw new IllegalArgumentException("Board with id " + post.getBoard().getId() + " does not exist");
        }

        if (post.getUser() == null || post.getUser().getId() == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (!userRepository.existsById(post.getUser().getId())) {
            throw new IllegalArgumentException("User with id " + post.getUser().getId() + " does not exist");
        }
    }
}