package com.example.chessproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;
import org.springframework.stereotype.Service;
import com.example.chessproject.model.ChessResponse;

@Service
public class ChessService {
    private final MoveService moveService;
    
    public ChessService(MoveService moveService) {
        this.moveService = moveService;
    }

    public ChessResponse bestMove(String whosTurn, Integer searchDepth, Map<String, String> board) {
        List<Map<String, String>> allPossibleMoves = moveService.legalMove(whosTurn, board);
        for (Map<String, String> possibleBoardState : allPossibleMoves) {
            if (moveService.checkCheckMate(whosTurn, possibleBoardState)) {
                AbstractMap.SimpleEntry<String, String> moveDetails = findMove(board, possibleBoardState);
                String pieceMoved = moveDetails.getKey();
                String moveNotation = moveDetails.getValue();
                System.out.println(pieceMoved);
                System.out.println(moveNotation);

                return new ChessResponse(pieceMoved, moveNotation, "Checkmate found");
            }
        }
        return new ChessResponse("Null", "Null", "No move found");
    }

    private AbstractMap.SimpleEntry<String, String> findMove(Map<String, String> initialBoard, Map<String, String> finalBoard) {
        String piece = null;
        String sourcePosition = null;
        String destinationPosition = null;

        // Iterate through the initial board to identify the piece that has moved and its new position
        for (Map.Entry<String, String> entry : initialBoard.entrySet()) {
            String position = entry.getKey();
            String initialPiece = entry.getValue();
            String finalPiece = finalBoard.get(position);

            // Find the source position (a piece was at this position initially but not at the same position in finalBoard)
            if (initialPiece != null && !initialPiece.equals("empty") && (finalPiece == null || finalPiece.equals("empty"))) {
                piece = initialPiece;
                sourcePosition = position;
            }

            // Find the destination position (a new piece appears in the final board that wasn't there initially)
            if ((initialPiece == null || initialPiece.equals("empty")) && finalPiece != null && !finalPiece.equals("empty")) {
                destinationPosition = position;
            }
        }

        // Check if both source and destination were found and return the move in the correct notation
        if (sourcePosition != null && destinationPosition != null) {
            return new AbstractMap.SimpleEntry<>(piece, sourcePosition + " to " + destinationPosition);
        } else {
            return null; // Return null if we can't determine the move
        }
    }
}

// package com.example.chessproject.service;

// import java.util.Map;
// import org.springframework.stereotype.Service;
// import com.example.chessproject.model.ChessResponse;

// @Service
// public class ChessService {
//     private final MinMaxService minMaxService;
//     private final MoveService moveService;

//     public ChessService(MinMaxService minMaxService, MoveService moveService) {
//         this.minMaxService = minMaxService;
//         this.moveService = moveService;
//     }

//     public ChessResponse bestMove(String whosTurn, Integer searchDepth, Map<String, String> board) {
//         boolean isMaximizingPlayer = whosTurn.equals("white");
//         Map<String, String> bestMove = minMaxService.findBestMove(board, searchDepth, isMaximizingPlayer);

//         if (bestMove == null) {
//             return new ChessResponse(null, null, "No legal moves available");
//         }

//         String piece = findPieceMoved(board, bestMove);
//         String moveNotation = convertToMoveNotation(board, bestMove, piece);
        
//         return new ChessResponse(piece, moveNotation, "Success");
//     }

//     private String findPieceMoved(Map<String, String> originalBoard, Map<String, String> newBoard) {
//         for (Map.Entry<String, String> entry : originalBoard.entrySet()) {
//             String originalPosition = entry.getKey();
//             String piece = entry.getValue();

//             if (!piece.equals("empty") && !newBoard.get(originalPosition).equals(piece)) {
//                 return piece;
//             }
//         }
//         return null;
//     }

//     private String convertToMoveNotation(Map<String, String> originalBoard, Map<String, String> newBoard, String piece) {
//         String startPosition = findPositionOfPiece(originalBoard, piece);
//         String endPosition = findPositionOfPiece(newBoard, piece);
//         return startPosition + "-" + endPosition;
//     }

//     private String findPositionOfPiece(Map<String, String> board, String piece) {
//         for (Map.Entry<String, String> entry : board.entrySet()) {
//             if (entry.getValue().equals(piece)) {
//                 return entry.getKey();
//             }
//         }
//         return null;
//     }
// }