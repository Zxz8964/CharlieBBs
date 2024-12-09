package com.itvictorkile.dao;

import com.itvictorkile.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 可以在这里添加自定义查询方法
    List<Board> findByParentId(Long parentId);
    Optional<Board> findByName(String name);

    @Query("SELECT b FROM Board b WHERE b.name LIKE %:keyword% OR b.description LIKE %:keyword%")
    Page<Board> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}