// package com.example.chessproject.service;

// import java.util.List;
// import java.util.Map;
// import org.springframework.stereotype.Service;

// @Service
// public class MinMaxService {
//     private final MoveService moveService;
//     private final EvaluationService evaluationService;

//     public MinMaxService(MoveService moveService, EvaluationService evaluationService) {
//         this.moveService = moveService;
//         this.evaluationService = evaluationService;
//     }

//     public Map<String, String> findBestMove(Map<String, String> board, int depth, boolean isMaximizingPlayer) {
//         List<Map<String, String>> possibleMoves = moveService.LegalMove(board);
        
//         if (possibleMoves.isEmpty()) {
//             return null;
//         }

//         Map<String, String> bestMove = null;
//         double bestScore = isMaximizingPlayer ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

//         for (Map<String, String> move : possibleMoves) {
//             double score = miniMaxScore(move, depth - 1, !isMaximizingPlayer);
            
//             if (isMaximizingPlayer) {
//                 if (score > bestScore) {
//                     bestScore = score;
//                     bestMove = move;
//                 }
//             } else {
//                 if (score < bestScore) {
//                     bestScore = score;
//                     bestMove = move;
//                 }
//             }
//         }

//         return bestMove;
//     }

//     private double miniMaxScore(Map<String, String> board, int depth, boolean isMaximizingPlayer) {
//         if (depth == 0) {
//             return evaluationService.evaluateBoardScore(board);
//         }

//         List<Map<String, String>> possibleMoves = moveService.LegalMove(board);
//         double bestScore = isMaximizingPlayer ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

//         for (Map<String, String> move : possibleMoves) {
//             double score = miniMaxScore(move, depth - 1, !isMaximizingPlayer);
            
//             bestScore = isMaximizingPlayer 
//                 ? Math.max(bestScore, score)
//                 : Math.min(bestScore, score);
//         }

//         return bestScore;
//     }
// }