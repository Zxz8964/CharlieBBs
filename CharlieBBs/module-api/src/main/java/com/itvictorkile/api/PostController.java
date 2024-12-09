package com.itvictorkile.api;

import com.itvictorkile.dao.BoardRepository;
import com.itvictorkile.dao.UserRepository;
import com.itvictorkile.dto.PostDTO;
import com.itvictorkile.entity.Board;
import com.itvictorkile.entity.Post;
import com.itvictorkile.entity.User;
import com.itvictorkile.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostDTO postDTO) {
        // 查找用户和板块
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Board board = boardRepository.findById(postDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        // 创建 Post 实体
        Post post = new Post();
        post.setUser(user);
        post.setBoard(board);
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());

        // 保存帖子
        Post createdPost = postService.createPost(post);

        // 返回自定义成功消息
        String successMessage = "用户 " + createdPost.getUser().getUsername() + " 的帖子创建成功";
        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Board board = boardRepository.findById(postDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        Post existingPost = postService.getPostById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        existingPost.setUser(user);
        existingPost.setBoard(board);
        existingPost.setTitle(postDTO.getTitle());
        existingPost.setContent(postDTO.getContent());

        Post updatedPost = postService.updatePost(id, existingPost);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
}