package com.itvictorkile.dao;

import com.itvictorkile.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    List<PostTag> findByPostId(Long postId);

    Optional<PostTag> findByPostIdAndTagsId(Long id, long id1);
}
