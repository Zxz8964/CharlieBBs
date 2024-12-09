package com.itvictorkile.dao;

import com.itvictorkile.entity.BoardTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {
    Optional<BoardTag> findByBoardIdAndTagsId(Long id, Long id1);
    List<BoardTag> findByBoardId(Long boardId);


}
