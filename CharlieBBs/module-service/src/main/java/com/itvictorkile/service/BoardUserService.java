package com.itvictorkile.service;

import com.itvictorkile.dto.BoardUserDTO;

import java.util.List;

public interface BoardUserService {
    List<BoardUserDTO> getAllBoardUsers();

    BoardUserDTO getBoardUserById(Long id);

    List<BoardUserDTO> getBoardUsersByBoardId(Long boardId);

    BoardUserDTO createBoardUser(BoardUserDTO boardUserDTO);

    BoardUserDTO updateBoardUser(Long id, BoardUserDTO boardUserDTO);

    void deleteBoardUser(Long id);
}

