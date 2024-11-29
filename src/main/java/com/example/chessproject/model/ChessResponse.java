package com.example.chessproject.model;

import java.util.Map;

public class ChessResponse {
    private String piece;
    private String move;
    private String statusMessage;

    public ChessResponse(String piece, String move, String statusMessage) {
        this.piece = piece;
        this.move = move;
        this.statusMessage = statusMessage;
    }

    public String getPiece() {
        return piece;
    }

    public String getMove() {
        return move;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
