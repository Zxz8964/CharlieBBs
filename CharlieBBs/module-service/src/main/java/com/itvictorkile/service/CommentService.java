package com.itvictorkile.service;

import com.itvictorkile.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);
    Comment updateComment(Long id, Comment comment);
    void deleteComment(Long id);
    Comment getCommentById(Long id);
    List<Comment> getCommentsByPostId(Long postId);
}