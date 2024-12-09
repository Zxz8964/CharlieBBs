package com.itvictorkile.service.impl;

import com.itvictorkile.dao.VoteActionRepository;
import com.itvictorkile.entity.VoteAction;
import com.itvictorkile.service.VoteActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteActionServiceImpl implements VoteActionService {

    @Autowired
    private VoteActionRepository voteActionRepository;

    @Override
    public void addAction(VoteAction action) {
        voteActionRepository.save(action);
    }

    @Override
    public List<VoteAction> getActionsByVoteId(Long voteId) {
        return voteActionRepository.findByVoteId(voteId);
    }

    @Override
    public void removeAction(Long actionId) {
        voteActionRepository.deleteById(actionId);
    }
}