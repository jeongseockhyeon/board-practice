package com.board_practice.board_practice.service;

import com.board_practice.board_practice.dto.BoardDTO;
import com.board_practice.board_practice.entity.BoardEntity;
import com.board_practice.board_practice.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO){
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);
    }
}
