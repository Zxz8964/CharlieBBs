package com.itvictorkile.service.impl;

import com.itvictorkile.dao.RoleRepository;
import com.itvictorkile.dao.TagRepository;
import com.itvictorkile.dto.TagsDTO;
import com.itvictorkile.entity.Tags;
import com.itvictorkile.service.TagsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagsServiceImpl implements TagsService {
    private final TagRepository tagRepository;
    private final RoleRepository roleRepository;

    public TagsServiceImpl(TagRepository tagRepository, RoleRepository roleRepository) {
        this.tagRepository = tagRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<TagsDTO> getAllTags() {
        return tagRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TagsDTO getTagById(Long id) {
        Tags tag = tagRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Tag not found with id: " + id));
        return convertToDTO(tag);
    }

    @Override
    public TagsDTO createTag(TagsDTO tagsDTO) {
        Optional<Tags> existingTag=tagRepository.findByName(tagsDTO.getName());
        if(existingTag.isPresent()) {
            throw new IllegalArgumentException("Tag with name "+tagsDTO.getName()+" already exists.");
        }
        Tags tags=new Tags();
        tags.setName(tagsDTO.getName());
        tags.setDescription(tagsDTO.getDescription());
        Tags saveTag = tagRepository.save(tags);
        return convertToDTO(saveTag);
    }

    @Override
    public TagsDTO updateTag(Long id, TagsDTO tagsDTO) {
        Tags tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found with id: " + id));
        tag.setName(tagsDTO.getName());
        tag.setDescription(tagsDTO.getDescription());
        Tags updateTag = tagRepository.save(tag);
        return convertToDTO(updateTag);
    }

    @Override
    public void deleteTags(Long id) {
        if(!tagRepository.existsById(id)) {
            throw new IllegalArgumentException("Tag not found with id: " + id);
        }
        tagRepository.deleteById(id);
    }

    @Override
    public Optional<Tags> findByName(String name) {
        return tagRepository.findByName(name);
    }
    private TagsDTO convertToDTO(Tags tags) {
        TagsDTO tagsDTO = new TagsDTO();
        tagsDTO.setId(tags.getId());
        tagsDTO.setName(tags.getName());
        tagsDTO.setDescription(tags.getDescription());
        return tagsDTO;
    }
}
