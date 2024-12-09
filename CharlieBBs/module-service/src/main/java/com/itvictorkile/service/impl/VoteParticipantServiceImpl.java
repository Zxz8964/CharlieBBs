package com.itvictorkile.service.impl;

import com.itvictorkile.dao.VoteParticipantRepository;
import com.itvictorkile.entity.VoteParticipant;
import com.itvictorkile.service.VoteParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteParticipantServiceImpl implements VoteParticipantService {

    @Autowired
    private VoteParticipantRepository voteParticipantRepository;

    @Override
    public void addParticipant(VoteParticipant participant) {
        voteParticipantRepository.save(participant);
    }

    @Override
    public List<VoteParticipant> getParticipantsByVoteId(Long voteId) {
        return voteParticipantRepository.findByVoteId(voteId);
    }

    @Override
    public void removeParticipant(Long participantId) {
        voteParticipantRepository.deleteById(participantId);
    }
}