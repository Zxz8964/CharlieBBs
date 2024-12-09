package com.itvictorkile.dto;

import com.itvictorkile.entity.Board;
import com.itvictorkile.entity.User;
import lombok.Data;

@Data
public class BoardUserDTO {
    private Long id;
    private Board board;
    private User user;
}
