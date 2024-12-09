package com.itvictorkile.service.impl;

import com.itvictorkile.dao.UserRepository;
import com.itvictorkile.dao.VoteRepository;
import com.itvictorkile.dto.CreateVoteDTO;
import com.itvictorkile.dto.VoteDTO;
import com.itvictorkile.entity.*;
import com.itvictorkile.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository; // 确保有 UserRepository 以查找用户

    @Override
    public VoteDTO createVote(CreateVoteDTO createVoteDTO) {
        Vote vote = new Vote();
        vote.setCreator(new User()); // 需要从 createVoteDTO 中设置创建者
        vote.setBoard(new Board());   // 需要从 createVoteDTO 中设置板块
        vote.setTitle(createVoteDTO.getTitle());
        vote.setDescription(createVoteDTO.getDescription());
        vote.setIsMultipleChoice(createVoteDTO.getOptions().size() > 1); // 根据选项数量判断是否为多选
        vote.setEndTime(LocalDateTime.parse(createVoteDTO.getEndTime())); // 转换 endTime

        // 设置选项
        for (String option : createVoteDTO.getOptions()) {
            VoteOption voteOption = new VoteOption();
            voteOption.setOptionText(option);
            voteOption.setVote(vote);
            vote.getOptions().add(voteOption);
        }

        // 设置参与者
        for (Long userId : createVoteDTO.getParticipantRoleIds()) { // 用 userId 而不是 roleId
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found for ID: " + userId)); // 找到用户

            VoteParticipant participant = new VoteParticipant();
            participant.setUser(user); // 设定用户
            participant.setVote(vote); // 设定投票
            vote.getParticipants().add(participant);
        }

        Vote savedVote = voteRepository.save(vote);
        return convertToDTO(savedVote);
    }

    @Override
    public VoteDTO getVoteById(Long id) {
        Vote vote = voteRepository.findById(id).orElseThrow(() -> new RuntimeException("Vote not found"));
        return convertToDTO(vote);
    }

    @Override
    public List<VoteDTO> getAllVotes() {
        return voteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteVote(Long id) {
        voteRepository.deleteById(id);
    }

    private VoteDTO convertToDTO(Vote vote) {
        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setId(vote.getId());
        voteDTO.setCreatorId(vote.getCreator().getId());
        voteDTO.setBoardId(vote.getBoard() != null ? vote.getBoard().getId() : null);
        voteDTO.setTitle(vote.getTitle());
        voteDTO.setDescription(vote.getDescription());
        voteDTO.setIsMultipleChoice(vote.getIsMultipleChoice());
        voteDTO.setEndTime(vote.getEndTime());
        // 这里可以设置选项 DTO
        return voteDTO;
    }
}