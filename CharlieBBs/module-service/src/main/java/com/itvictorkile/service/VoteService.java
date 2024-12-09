package com.itvictorkile.service;

import com.itvictorkile.dto.CreateVoteDTO;
import com.itvictorkile.dto.VoteDTO;

import java.util.List;

public interface VoteService {
    VoteDTO createVote(CreateVoteDTO createVoteDTO); // 修改为使用 CreateVoteDTO
    VoteDTO getVoteById(Long id);
    List<VoteDTO> getAllVotes();
    void deleteVote(Long id);
}