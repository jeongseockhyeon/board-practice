package com.board_practice.board_practice.controller;

import com.board_practice.board_practice.dto.CommentDTO;
import com.board_practice.board_practice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public @ResponseBody  ResponseEntity<?> commentSave(@RequestBody CommentDTO commentDTO){
        System.out.println("commentDTO = " + commentDTO);
        Long saveResult = commentService.save(commentDTO);
        if (saveResult != null){
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(commentDTOList);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("해당 게시글이 존재하지 않습니다");
        }

    }
}
