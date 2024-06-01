package com.board_practice.board_practice.controller;

import com.board_practice.board_practice.dto.BoardDTO;
import com.board_practice.board_practice.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        List<BoardDTO> boardDTOList = boardService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        /*
        * 해당 게시글의 조회수 증가
        * 게시글 데이터를 가져와서 상세조회*/

        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardDTO);
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody BoardDTO boardDTO) {
        boardService.update(id, boardDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("수정되었습니다");
    }
}
