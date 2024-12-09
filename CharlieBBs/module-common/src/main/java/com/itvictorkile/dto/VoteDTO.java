package com.itvictorkile.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VoteDTO {
    private Long id;
    private Long creatorId;
    private Long boardId;
    private String title;
    private String description;
    private Boolean isMultipleChoice;
    private LocalDateTime endTime;
    private List<VoteOptionDTO> options;
}
