package com.itvictorkile.api;

import com.itvictorkile.dto.TagsDTO;
import com.itvictorkile.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagsService tagsService;
    @Autowired
    public TagController(TagsService tagsService) {
        this.tagsService = tagsService;
    }
    @GetMapping
    public ResponseEntity<List<TagsDTO>> getAllTags() {
        List<TagsDTO> tags = tagsService.getAllTags();
        return ResponseEntity.ok(tags);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TagsDTO> getTagsByid(@PathVariable Long id) {
        TagsDTO tag = tagsService.getTagById(id);
        return ResponseEntity.ok(tag);
    }
    @PostMapping
    public ResponseEntity<TagsDTO> createTags(@RequestBody TagsDTO tagsDTO) {
        TagsDTO newTag = tagsService.createTag(tagsDTO);
        return ResponseEntity.ok(newTag);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TagsDTO> updateTagsByid(@PathVariable Long id, @RequestBody TagsDTO tagsDTO) {
        TagsDTO updateTag = tagsService.updateTag(id, tagsDTO);
        return ResponseEntity.ok(updateTag);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deleteTagsByid(@PathVariable Long id) {
        tagsService.deleteTags(id);
        return ResponseEntity.noContent().build();
    }
}
