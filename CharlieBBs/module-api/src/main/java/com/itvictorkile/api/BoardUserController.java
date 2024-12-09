package com.itvictorkile.api;

import com.itvictorkile.dto.BoardUserDTO;
import com.itvictorkile.service.BoardUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boardusers")
public class BoardUserController {
    private final BoardUserService boardUserService;

    @Autowired
    public BoardUserController(BoardUserService boardUserService) {
        this.boardUserService = boardUserService;
    }

    @GetMapping
    public ResponseEntity<List<BoardUserDTO>> getAllBoardUsers() {
        List<BoardUserDTO> boardUsers = boardUserService.getAllBoardUsers();
        return ResponseEntity.ok(boardUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardUserDTO> getBoardUserById(@PathVariable Long id) {
        BoardUserDTO boardUser = boardUserService.getBoardUserById(id);
        return ResponseEntity.ok(boardUser);
    }

    @PostMapping
    public ResponseEntity<BoardUserDTO> createBoardUser(@RequestBody BoardUserDTO boardUserDTO) {
        BoardUserDTO newBoardUser = boardUserService.createBoardUser(boardUserDTO);
        return ResponseEntity.ok(newBoardUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardUserDTO> updateBoardUser(@PathVariable Long id, @RequestBody BoardUserDTO boardUserDTO) {
        BoardUserDTO updatedBoardUser = boardUserService.updateBoardUser(id, boardUserDTO);
        return ResponseEntity.ok(updatedBoardUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardUser(@PathVariable Long id) {
        boardUserService.deleteBoardUser(id);
        return ResponseEntity.noContent().build();
    }
}

