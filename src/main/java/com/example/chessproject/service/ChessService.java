package com.example.chessproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.example.chessproject.dto.ChessMoveRequest;
import com.example.chessproject.dto.ChessSquare;

@Service
public class ChessService {

    public String predictMove(ChessMoveRequest request) {
        return 'd';
    }
}