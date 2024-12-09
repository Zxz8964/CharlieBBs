package com.itvictorkile.service;

import com.itvictorkile.dto.BoardTagDTO;

import java.util.List;

public interface BoardTagService {
    List<BoardTagDTO> getAllBoardTags();
    BoardTagDTO getBoardTagById(Long id);
    List<BoardTagDTO> getBoardTagsByBoardId(Long boardId);
    BoardTagDTO createBoardTag(BoardTagDTO boardTagDTO);
    BoardTagDTO updateBoardTag(Long id,BoardTagDTO boardTagDTO);
    void deleteBoardTag(Long id);
}
