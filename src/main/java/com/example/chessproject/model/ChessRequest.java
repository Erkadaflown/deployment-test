package com.example.chessproject.model;

import java.util.Map;

public class ChessRequest {
    private String whosTurn;
    private Integer searchDepth;
    private Map<String, String> board;

    public String getWhosTurn() {
        return whosTurn;
    }

    public Integer getSearchDepth() {
        return searchDepth;
    }

    public Map<String, String> getBoard() {
        return board;
    }
}