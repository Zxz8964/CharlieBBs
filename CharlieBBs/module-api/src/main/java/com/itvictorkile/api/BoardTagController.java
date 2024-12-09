package com.itvictorkile.api;

import com.itvictorkile.service.BoardTagService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/BoardTags")
public class BoardTagController {
    private final BoardTagService boardTagService;

    public BoardTagController(BoardTagService boardTagService) {
        this.boardTagService = boardTagService;
    }

}
