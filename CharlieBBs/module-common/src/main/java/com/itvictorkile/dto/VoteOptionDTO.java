package com.itvictorkile.dto;

import lombok.Data;

@Data
public class VoteOptionDTO {
    private Long id;
    private String optionText;
    private Long votesCount;
}
