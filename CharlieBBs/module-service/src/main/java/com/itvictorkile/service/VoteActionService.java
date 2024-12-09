package com.itvictorkile.service;

import com.itvictorkile.entity.VoteAction;

import java.util.List;


public interface VoteActionService {
    void addAction(VoteAction action);
    List<VoteAction> getActionsByVoteId(Long voteId);
    void removeAction(Long actionId);
}