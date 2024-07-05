package com.board_practice.board_practice.controller;

import com.board_practice.board_practice.dto.CommetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    @PostMapping("/save/{id}")
    public @ResponseBody  ResponseEntity<?> commentSave(@ModelAttribute CommetDTO commetDTO){
        System.out.println("commentDTO = " + commetDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("댓글 작성");
    }
}
