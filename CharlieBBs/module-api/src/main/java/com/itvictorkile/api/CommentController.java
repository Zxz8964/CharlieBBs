package com.itvictorkile.api;

import com.itvictorkile.dto.CommentDTO;
import com.itvictorkile.entity.Comment;
import com.itvictorkile.entity.Post;
import com.itvictorkile.entity.User;
import com.itvictorkile.service.CommentService;
import com.itvictorkile.service.PostService;
import com.itvictorkile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO commentDTO) {
        // 查找用户和帖子
        User user = userService.getUserById(commentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postService.getPostById(commentDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // 创建 Comment 实体
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());

        // 保存并返回
        Comment createdComment = commentService.createComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        // 验证传入的 Comment 对象
        if (comment.getPost() == null || comment.getPost().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        if (comment.getUser() == null || comment.getUser().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // 调用 service 方法更新评论
        Comment updatedComment = commentService.updateComment(id, comment);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
}