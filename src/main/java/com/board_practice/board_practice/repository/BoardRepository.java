package com.board_practice.board_practice.repository;

import com.board_practice.board_practice.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
