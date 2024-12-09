package com.itvictorkile.service.impl;

import com.itvictorkile.dao.BoardUserRepository;
import com.itvictorkile.dto.BoardUserDTO;
import com.itvictorkile.entity.BoardUser;
import com.itvictorkile.service.BoardUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardUserServiceImpl implements BoardUserService {
    private final BoardUserRepository boardUserRepository;

    @Autowired
    public BoardUserServiceImpl(BoardUserRepository boardUserRepository) {
        this.boardUserRepository = boardUserRepository;
    }

    @Override
    public List<BoardUserDTO> getAllBoardUsers() {
        return boardUserRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BoardUserDTO getBoardUserById(Long id) {
        BoardUser boardUser = boardUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BoardUser not found with id: " + id));
        return convertToDTO(boardUser);
    }

    @Override
    public List<BoardUserDTO> getBoardUsersByBoardId(Long boardId) {
        List<BoardUser> boardUsers = boardUserRepository.findByBoardId(boardId);
        if (boardUsers.isEmpty()) {
            throw new IllegalArgumentException("No users found for board with id: " + boardId);
        }
        return boardUsers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BoardUserDTO createBoardUser(BoardUserDTO boardUserDTO) {
        // 检查是否存在同样的板块用户
        Optional<BoardUser> existingBoardUser = boardUserRepository.findByUserIdAndBoardId(boardUserDTO.getUser().getId(), boardUserDTO.getBoard().getId());
        if (existingBoardUser.isPresent()) {
            throw new IllegalArgumentException("BoardUser with userId " + boardUserDTO.getUser().getId() + " and boardId " + boardUserDTO.getBoard().getId() + " already exists.");
        }

        // 创建新的板块用户
        BoardUser boardUser = new BoardUser();
        boardUser.setUser(boardUserDTO.getUser());
        boardUser.setBoard(boardUserDTO.getBoard());

        BoardUser savedBoardUser = boardUserRepository.save(boardUser);
        return convertToDTO(savedBoardUser);
    }

    @Override
    public BoardUserDTO updateBoardUser(Long id, BoardUserDTO boardUserDTO) {
        BoardUser boardUser = boardUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BoardUser not found with id: " + id));

        boardUser.setUser(boardUserDTO.getUser());
        boardUser.setBoard(boardUserDTO.getBoard());

        BoardUser updatedBoardUser = boardUserRepository.save(boardUser);
        return convertToDTO(updatedBoardUser);
    }

    @Override
    public void deleteBoardUser(Long id) {
        if (!boardUserRepository.existsById(id)) {
            throw new IllegalArgumentException("BoardUser not found with id: " + id);
        }
        boardUserRepository.deleteById(id);
    }

    private BoardUserDTO convertToDTO(BoardUser boardUser) {
        BoardUserDTO boardUserDTO = new BoardUserDTO();
        boardUserDTO.setId(boardUser.getId());
        boardUserDTO.setUser(boardUser.getUser());
        boardUserDTO.setBoard(boardUser.getBoard());
        return boardUserDTO;
    }
}

