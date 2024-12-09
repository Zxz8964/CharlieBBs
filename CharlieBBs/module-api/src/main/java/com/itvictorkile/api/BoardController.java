package com.itvictorkile.api;

import com.itvictorkile.dto.BoardDTO;
import com.itvictorkile.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@Validated
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(@Valid @RequestBody BoardDTO boardDTO) {
        BoardDTO createdBoard = boardService.createBoard(boardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable Long id, @Valid @RequestBody BoardDTO boardDTO) {
        BoardDTO updatedBoard = boardService.updateBoard(id, boardDTO);
        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable Long id) {
        BoardDTO board = boardService.getBoardById(id);
        return ResponseEntity.ok(board);
    }

    @GetMapping
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        List<BoardDTO> boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{id}/subboards")
    public ResponseEntity<List<BoardDTO>> getSubBoards(@PathVariable Long id) {
        List<BoardDTO> subBoards = boardService.getSubBoards(id);
        return ResponseEntity.ok(subBoards);
    }
}