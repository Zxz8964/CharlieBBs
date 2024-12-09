package com.itvictorkile.service;

import com.itvictorkile.entity.VoteOption;

import java.util.List;

public interface VoteOptionService {
    void addOption(VoteOption option);
    List<VoteOption> getOptionsByVoteId(Long voteId);
    void removeOption(Long optionId);
}
