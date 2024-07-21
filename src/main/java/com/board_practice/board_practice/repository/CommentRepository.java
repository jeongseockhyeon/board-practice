package com.board_practice.board_practice.repository;

import com.board_practice.board_practice.entity.BoardEntity;
import com.board_practice.board_practice.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);

}
