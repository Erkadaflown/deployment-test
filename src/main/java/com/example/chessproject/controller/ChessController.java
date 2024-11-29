package com.example.chessproject.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.chessproject.model.ChessRequest;
import com.example.chessproject.model.ChessResponse;
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
    @ResponseBody
    public ChessResponse predictBestMove(@RequestBody ChessRequest chessRequest) {
        String whosTurn = chessRequest.getWhosTurn();
        Integer searchDepth = chessRequest.getSearchDepth();
        Map<String, String> board = chessRequest.getBoard();
        
        ChessResponse response = chessService.bestMove(whosTurn, searchDepth, board);
        
        return response;
    }
}
