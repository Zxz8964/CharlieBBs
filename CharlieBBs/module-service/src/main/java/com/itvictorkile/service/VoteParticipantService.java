package com.itvictorkile.service;

import com.itvictorkile.entity.VoteParticipant;

import java.util.List;

public interface VoteParticipantService {
    void addParticipant(VoteParticipant participant);
    List<VoteParticipant> getParticipantsByVoteId(Long voteId);
    void removeParticipant(Long participantId);
}
