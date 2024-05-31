package com.board_practice.board_practice.controller;

import com.board_practice.board_practice.dto.BoardDTO;
import com.board_practice.board_practice.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody BoardDTO boardDTO){
        boardService.save(boardDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("저장되었습니다");
    }

}
