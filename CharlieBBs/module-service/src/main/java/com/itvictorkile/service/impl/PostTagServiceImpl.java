package com.itvictorkile.service.impl;

import com.itvictorkile.dao.PostTagRepository;
import com.itvictorkile.dto.PostTagDTO;
import com.itvictorkile.entity.PostTag;
import com.itvictorkile.service.PostTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostTagServiceImpl implements PostTagService {
    private final PostTagRepository postTagRepository;

    @Autowired
    public PostTagServiceImpl(PostTagRepository postTagRepository) {
        this.postTagRepository = postTagRepository;
    }

    @Override
    public List<PostTagDTO> getAllPostTags() {
        return postTagRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostTagDTO getPostTagById(Long id) {
        PostTag postTag = postTagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PostTag not found with id: " + id));
        return convertToDTO(postTag);
    }

    @Override
    public List<PostTagDTO> getPostTagsByPostId(Long postId) {
        List<PostTag> postTags = postTagRepository.findByPostId(postId);
        if (postTags.isEmpty()) {
            throw new IllegalArgumentException("No tags found for post with id: " + postId);
        }
        return postTags.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostTagDTO createPostTag(PostTagDTO postTagDTO) {
        Optional<PostTag> existingPostTag = postTagRepository.findByPostIdAndTagsId(postTagDTO.getPost().getId(), postTagDTO.getTag().getId());
        if (existingPostTag.isPresent()) {
            throw new IllegalArgumentException("PostTag with postId " + postTagDTO.getPost().getId() + " and tagId " + postTagDTO.getTag().getId() + " already exists.");
        }

        PostTag postTag = new PostTag();
        postTag.setPost(postTagDTO.getPost());
        postTag.setTags(postTagDTO.getTag());

        PostTag savedPostTag = postTagRepository.save(postTag);
        return convertToDTO(savedPostTag);
    }

    @Override
    public PostTagDTO updatePostTag(Long id, PostTagDTO postTagDTO) {
        PostTag postTag = postTagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PostTag not found with id: " + id));

        postTag.setPost(postTagDTO.getPost());
        postTag.setTags(postTagDTO.getTag());

        PostTag updatedPostTag = postTagRepository.save(postTag);
        return convertToDTO(updatedPostTag);
    }

    @Override
    public void deletePostTag(Long id) {
        if (!postTagRepository.existsById(id)) {
            throw new IllegalArgumentException("PostTag not found with id: " + id);
        }
        postTagRepository.deleteById(id);
    }

    private PostTagDTO convertToDTO(PostTag postTag) {
        PostTagDTO postTagDTO = new PostTagDTO();
        postTagDTO.setId(postTag.getId());
        postTagDTO.setPost(postTag.getPost());
        postTagDTO.setTag(postTag.getTags());
        return postTagDTO;
    }
}
