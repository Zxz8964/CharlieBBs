package com.itvictorkile.service.impl;

import com.itvictorkile.dao.BoardTagRepository;
import com.itvictorkile.dto.BoardTagDTO;
import com.itvictorkile.entity.BoardTag;
import com.itvictorkile.service.BoardTagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardTagServiceImpl implements BoardTagService {
    private BoardTagRepository boardTagRepository;

    public BoardTagServiceImpl(BoardTagRepository boardTagRepository) {
        this.boardTagRepository = boardTagRepository;
    }

    @Override
    public List<BoardTagDTO> getAllBoardTags() {
        return boardTagRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BoardTagDTO getBoardTagById(Long id) {
        BoardTag boardTag = boardTagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BoardTag not found with id: " + id));
        return convertToDTO(boardTag);
    }

    @Override
    public List<BoardTagDTO> getBoardTagsByBoardId(Long boardId) {
        List<BoardTag> boardTags= boardTagRepository.findByBoardId(boardId);
        if(boardTags.isEmpty()){
            throw new IllegalArgumentException("BoardTag not found with id: " + boardId);
        }
        return boardTags.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    @Override
    public BoardTagDTO createBoardTag(BoardTagDTO boardTagDTO) {
        Optional<BoardTag> existingBoardTag = boardTagRepository.findByBoardIdAndTagsId(boardTagDTO.getBoard().getId(), boardTagDTO.getTags().getId());
        if(existingBoardTag.isPresent()){
            throw new IllegalArgumentException("BoardTag with Boardid: " + boardTagDTO.getBoard().getId() +"and TagId"+boardTagDTO.getTags().getId()+ " already exists");
        }
        BoardTag boardTag = new BoardTag();
        boardTag.setBoard(boardTagDTO.getBoard());
        boardTag.setTags(boardTagDTO.getTags());
        BoardTag savedBoardTag = boardTagRepository.save(boardTag);
        return convertToDTO(savedBoardTag);
    }

    @Override
    public BoardTagDTO updateBoardTag(Long id, BoardTagDTO boardTagDTO) {
        BoardTag boardTag = boardTagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BoardTag not found with id: " + id));
        boardTag.setBoard(boardTagDTO.getBoard());
        boardTag.setTags(boardTagDTO.getTags());
        BoardTag updateBoardTag = boardTagRepository.save(boardTag);
        return convertToDTO(updateBoardTag);
    }

    @Override
    public void deleteBoardTag(Long id) {
        if(!boardTagRepository.existsById(id)){
            throw new IllegalArgumentException("BoardTag not found with id: " + id);
        }
        boardTagRepository.deleteById(id);
    }
    private BoardTagDTO convertToDTO(BoardTag boardTag) {
        BoardTagDTO boardTagDTO = new BoardTagDTO();
        boardTagDTO.setId(boardTagDTO.getId());
        boardTagDTO.setBoard(boardTag.getBoard());
        boardTagDTO.setTags(boardTag.getTags());
        return boardTagDTO;
    }
}
