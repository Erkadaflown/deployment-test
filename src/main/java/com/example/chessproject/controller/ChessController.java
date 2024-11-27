package com.example.chessproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.chessproject.dto.ChessMoveRequest;
import com.example.chessproject.dto.ChessMoveResponse;
import com.example.chessproject.service.ChessService;

@Controller
public class ChessController {

    private final ChessService chessService;

    public ChessController(ChessService chessService) {
        this.chessService = chessService;
    }

    @GetMapping("/")
    public String chess() {
        return "index";
    }

    @PostMapping("/predict")
    public predictBestMove(request) {
        try {
            String bestMove = chessService.predictMove(request);
            return ResponseEntity.ok(new ChessMoveResponse(bestMove));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ChessMoveResponse(null, e.getMessage()));
        }
    }
}