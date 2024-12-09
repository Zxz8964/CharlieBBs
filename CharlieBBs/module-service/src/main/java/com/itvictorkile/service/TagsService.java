package com.itvictorkile.service;

import com.itvictorkile.dto.TagsDTO;
import com.itvictorkile.entity.Tags;

import java.util.List;
import java.util.Optional;

public interface TagsService {
    List<TagsDTO> getAllTags();

    TagsDTO getTagById(Long id);

    TagsDTO createTag(TagsDTO tagsDTO);

    TagsDTO updateTag(Long id, TagsDTO tagsDTO);

    void deleteTags(Long id);
    Optional<Tags> findByName(String name);
}
