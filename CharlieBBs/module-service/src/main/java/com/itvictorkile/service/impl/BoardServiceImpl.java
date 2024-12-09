package com.itvictorkile.service.impl;

import com.itvictorkile.dao.BoardRepository;
import com.itvictorkile.dto.BoardDTO;
import com.itvictorkile.entity.Board;
import com.itvictorkile.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public BoardDTO createBoard(BoardDTO boardDTO) {
        // 检查是否存在同名板块
        Optional<Board> existingBoard = boardRepository.findByName(boardDTO.getName());
        if (existingBoard.isPresent()) {
            throw new IllegalArgumentException("Board with name " + boardDTO.getName() + " already exists.");
        }

        // 创建新的板块
        Board board = new Board();
        board.setName(boardDTO.getName());
        board.setDescription(boardDTO.getDescription());
        board.setCreatedAt(LocalDateTime.now());
        board.setUpdatedAt(LocalDateTime.now());

        if (boardDTO.getParentId() != null) {
            Board parent = boardRepository.findById(boardDTO.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent board not found"));
            board.setParent(parent);
        }

        Board savedBoard = boardRepository.save(board);
        return convertToDTO(savedBoard);
    }

    @Override
    public BoardDTO updateBoard(Long id, BoardDTO boardDTO) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found with id: " + id));

        board.setName(boardDTO.getName());
        board.setDescription(boardDTO.getDescription());
        board.setUpdatedAt(LocalDateTime.now());

        if (boardDTO.getParentId() != null) {
            Board parent = boardRepository.findById(boardDTO.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent board not found"));
            board.setParent(parent);
        } else {
            board.setParent(null);
        }

        Board updatedBoard = boardRepository.save(board);
        return convertToDTO(updatedBoard);
    }

    @Override
    public void deleteBoard(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new IllegalArgumentException("Board not found with id: " + id);
        }
        boardRepository.deleteById(id);
    }

    @Override
    public BoardDTO getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found with id: " + id));
        return convertToDTO(board);
    }

    @Override
    public List<BoardDTO> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardDTO> getSubBoards(Long parentId) {
        return boardRepository.findByParentId(parentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Board> findByName(String name) {
        return boardRepository.findByName(name);
    }

    private BoardDTO convertToDTO(Board board) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(board.getId());
        boardDTO.setName(board.getName());
        boardDTO.setDescription(board.getDescription());
        // 省略时间戳字段，因为 DTO 不需要它们
        // boardDTO.setCreatedAt(board.getCreatedAt());
        // boardDTO.setUpdatedAt(board.getUpdatedAt());

        if (board.getParent() != null) {
            boardDTO.setParentId(board.getParent().getId());
        }

        if (board.getChildren() != null) {
            List<BoardDTO> childrenDTOs = board.getChildren().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            boardDTO.setChildren(childrenDTOs);
        }

        return boardDTO;
    }
}