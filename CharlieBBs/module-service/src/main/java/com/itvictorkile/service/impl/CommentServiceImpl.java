package com.itvictorkile.service.impl;

import com.itvictorkile.dao.CommentRepository;
import com.itvictorkile.dao.PostRepository;
import com.itvictorkile.dao.UserRepository;
import com.itvictorkile.entity.Comment;
import com.itvictorkile.entity.Post;
import com.itvictorkile.entity.User;
import com.itvictorkile.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Comment createComment(Comment comment) {
        // 验证 Post 和 User 存在
        Post post = postRepository.findById(comment.getPost().getId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        User user = userRepository.findById(comment.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 设置关联
        comment.setPost(post);
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long id, Comment comment) {
        // 验证评论的 ID 是否有效
        if (!commentRepository.existsById(id)) {
            throw new IllegalArgumentException("Comment not found with id: " + id);
        }

        // 验证 Post 和 User 是否有效
        if (comment.getPost() == null || comment.getPost().getId() == null) {
            throw new IllegalArgumentException("Post cannot be null or missing ID");
        }
        if (comment.getUser() == null || comment.getUser().getId() == null) {
            throw new IllegalArgumentException("User cannot be null or missing ID");
        }

        comment.setId(id);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}