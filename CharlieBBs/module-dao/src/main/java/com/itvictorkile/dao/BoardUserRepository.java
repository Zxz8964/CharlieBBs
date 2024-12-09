package com.itvictorkile.dao;

import com.itvictorkile.entity.BoardUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {
    Optional<BoardUser> findByUserIdAndBoardId(Long id, Long id1);
    List<BoardUser> findByBoardId(Long boardId);
}
