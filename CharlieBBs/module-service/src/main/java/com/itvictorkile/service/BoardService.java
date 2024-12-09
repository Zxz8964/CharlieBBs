package com.itvictorkile.service;

import com.itvictorkile.dto.BoardDTO;
import com.itvictorkile.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    BoardDTO createBoard(BoardDTO boardDTO);
    BoardDTO updateBoard(Long id, BoardDTO boardDTO);
    void deleteBoard(Long id);
    BoardDTO getBoardById(Long id);
    List<BoardDTO> getAllBoards();
    List<BoardDTO> getSubBoards(Long parentId);
    Optional<Board> findByName(String name);
}