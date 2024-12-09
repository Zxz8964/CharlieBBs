package com.itvictorkile.dto;

import com.itvictorkile.entity.Board;
import com.itvictorkile.entity.Tags;
import lombok.Data;

@Data
public class BoardTagDTO {
    private Long id;
    private Board board;
    private Tags tags;
}
