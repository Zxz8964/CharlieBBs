package com.itvictorkile.dao;

import com.itvictorkile.entity.VoteParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteParticipantRepository extends JpaRepository<VoteParticipant, Long> {
    List<VoteParticipant> findByVoteId(Long voteId);
}
