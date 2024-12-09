package com.itvictorkile.dto;

import lombok.Data;

@Data
public class VoteParticipantDTO {
    private Long id;
    private Long voteId;
    private Long userId;
}
