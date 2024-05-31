package com.board_practice.board_practice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<?> index(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("홈입니다");
    }
}
