package com.itvictorkile.service.impl;

import com.itvictorkile.dao.VoteOptionRepository;
import com.itvictorkile.entity.VoteOption;
import com.itvictorkile.service.VoteOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteOptionServiceImpl implements VoteOptionService {

    @Autowired
    private VoteOptionRepository voteOptionRepository;

    @Override
    public void addOption(VoteOption option) {
        voteOptionRepository.save(option);
    }

    @Override
    public List<VoteOption> getOptionsByVoteId(Long voteId) {
        return voteOptionRepository.findByVoteId(voteId);
    }

    @Override
    public void removeOption(Long optionId) {
        voteOptionRepository.deleteById(optionId);
    }
}
