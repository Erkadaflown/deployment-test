// package com.example.chessproject.service;

// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import org.springframework.stereotype.Service;

// @Service
// public class EvaluationService {
//     private final MoveService moveService;

//     public EvaluationService(MoveService moveService) {
//         this.moveService = moveService;
//     }

//     public double evaluateBoardScore(Map<String, String> board) {
//         List<Map<String, String>> possibleMoves = moveService.LegalMove(board);
//         if (possibleMoves.isEmpty()) {
//             return isKingInCheck(board) ? Double.NEGATIVE_INFINITY : 0;
//         }

//         double whiteScore = 0;
//         double blackScore = 0;
//         Map<String, Double> piecePositionWeights = getPiecePositionWeights();

//         for (Map.Entry<String, String> entry : board.entrySet()) {
//             String piece = entry.getValue();
//             if (piece.equals("empty")) continue;

//             double pieceValue = getPieceValue(piece);
//             double positionBonus = getPiecePositionBonus(piece, entry.getKey(), piecePositionWeights);

//             if (piece.startsWith("w")) {
//                 whiteScore += pieceValue + positionBonus;
//             } else {
//                 blackScore += pieceValue + positionBonus;
//             }
//         }

//         // Additional bonus for king safety and development
//         whiteScore += evaluateKingSafety(board, "w");
//         blackScore += evaluateKingSafety(board, "b");

//         return whiteScore - blackScore;
//     }

//     private boolean isKingInCheck(Map<String, String> board) {
//         String kingToCheck = board.entrySet().stream()
//             .filter(entry -> entry.getValue().equals("wK"))
//             .findFirst()
//             .map(Map.Entry::getKey)
//             .orElse(null);

//         if (kingToCheck == null) {
//             kingToCheck = board.entrySet().stream()
//                 .filter(entry -> entry.getValue().equals("bK"))
//                 .findFirst()
//                 .map(Map.Entry::getKey)
//                 .orElse(null);
//         }

//         return kingToCheck != null && isSquareThreatened(kingToCheck, board);
//     }

//     private boolean isSquareThreatened(String square, Map<String, String> board) {
//         for (Map.Entry<String, String> entry : board.entrySet()) {
//             String piece = entry.getValue();
//             if (piece.equals("empty") || piece.startsWith(square.startsWith("w") ? "w" : "b")) continue;
            
//             List<Map<String, String>> possibleMoves = moveService.LegalMove(board);
//             if (possibleMoves.stream().anyMatch(move -> move.containsKey(square))) {
//                 return true;
//             }
//         }
//         return false;
//     }

//     private double evaluateKingSafety(Map<String, String> board, String color) {
//         double kingSafetyBonus = 0;
        
//         String[] developmentPieces = color.equals("w") 
//             ? new String[]{"wN", "wB"} 
//             : new String[]{"bN", "bB"};
        
//         for (String piece : developmentPieces) {
//             if (board.containsValue(piece)) {
//                 kingSafetyBonus += 0.5;
//             }
//         }

//         return kingSafetyBonus;
//     }

//     public double getPieceValue(String piece) {
//         return switch (piece) {
//             case "wP", "bP" -> 1.0;
//             case "wN", "bN" -> 3.0;
//             case "wB", "bB" -> 3.0;
//             case "wR", "bR" -> 5.0;
//             case "wQ", "bQ" -> 9.0;
//             case "wK", "bK" -> 100.0;
//             default -> 0.0;
//         };
//     }

//     private Map<String, Double> getPiecePositionWeights() {
//         Map<String, Double> weights = new HashMap<>();
        
//         // Center control bonus
//         String[] centerSquares = {"d4", "d5", "e4", "e5"};
//         for (String square : centerSquares) {
//             weights.put(square, 0.5);
//         }

//         return weights;
//     }

//     private double getPiecePositionBonus(String piece, String square, Map<String, Double> positionWeights) {
//         return positionWeights.getOrDefault(square, 0.0);
//     }
// }