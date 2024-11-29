package com.example.chessproject.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.chessproject.model.ChessResponse;

@Service
public class ChessService {

    private static final Logger logger = LoggerFactory.getLogger(ChessService.class);
    private final MoveService moveService;

    public ChessService(MoveService moveService) {
        this.moveService = moveService;
    }

    public ChessResponse bestMove(String whosTurn, Integer searchDepth, Map<String, String> board) {

        List<Map<String, String>> possibleMoves = moveService.LegalMove(board);
        System.out.println("---------------------------");
        System.out.println(possibleMoves);
        System.out.println("---------------------------");
        String piece = "wK";
        String move = "a2-a3";
        String statusMessage = "Success";
      
        return new ChessResponse(piece, move, statusMessage);
    }
}