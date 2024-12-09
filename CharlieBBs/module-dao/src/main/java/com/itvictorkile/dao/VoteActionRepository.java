package com.itvictorkile.dao;

import com.itvictorkile.entity.VoteAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteActionRepository extends JpaRepository<VoteAction, Long> {
    List<VoteAction> findByVoteId(Long voteId);
}
