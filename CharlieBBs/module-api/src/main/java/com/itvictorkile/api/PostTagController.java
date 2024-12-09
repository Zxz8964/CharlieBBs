package com.itvictorkile.api;

import com.itvictorkile.dto.PostTagDTO;
import com.itvictorkile.service.PostTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posttags")
public class PostTagController {
    private final PostTagService postTagService;

    @Autowired
    public PostTagController(PostTagService postTagService) {
        this.postTagService = postTagService;
    }

    @GetMapping
    public ResponseEntity<List<PostTagDTO>> getAllPostTags() {
        List<PostTagDTO> postTags = postTagService.getAllPostTags();
        return ResponseEntity.ok(postTags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostTagDTO> getPostTagById(@PathVariable Long id) {
        PostTagDTO postTag = postTagService.getPostTagById(id);
        return ResponseEntity.ok(postTag);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PostTagDTO>> getPostTagsByPostId(@PathVariable Long postId) {
        List<PostTagDTO> postTags = postTagService.getPostTagsByPostId(postId);
        return ResponseEntity.ok(postTags);
    }

    @PostMapping
    public ResponseEntity<PostTagDTO> createPostTag(@RequestBody PostTagDTO postTagDTO) {
        PostTagDTO newPostTag = postTagService.createPostTag(postTagDTO);
        return ResponseEntity.ok(newPostTag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostTagDTO> updatePostTag(@PathVariable Long id, @RequestBody PostTagDTO postTagDTO) {
        PostTagDTO updatedPostTag = postTagService.updatePostTag(id, postTagDTO);
        return ResponseEntity.ok(updatedPostTag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostTag(@PathVariable Long id) {
        postTagService.deletePostTag(id);
        return ResponseEntity.noContent().build();
    }
}
