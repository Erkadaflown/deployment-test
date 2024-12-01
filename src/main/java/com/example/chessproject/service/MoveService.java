package com.example.chessproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class MoveService {

    public List<Map<String, String>> legalMove(String whosTurn, Map<String, String> boardState) {
        List<Map<String, String>> allPossibleMoves = new ArrayList<>();
        String currentPlayerPrefix = whosTurn.equals("white-turn") ? "w" : "b";

        for (Map.Entry<String, String> entry : boardState.entrySet()) {
            String position = entry.getKey();
            String piece = entry.getValue();
            if (piece != null && piece.startsWith(currentPlayerPrefix)) {
                if (piece.equals("wP") || piece.equals("bP")) {
                    allPossibleMoves.addAll(pawnLegalMove(position, piece, boardState));
                } else if (piece.equals("wR") || piece.equals("bR")) {
                    allPossibleMoves.addAll(rookLegalMove(position, piece, boardState));
                } else if (piece.equals("wB") || piece.equals("bB")) {
                    allPossibleMoves.addAll(bishopLegalMove(position, piece, boardState));
                } else if (piece.equals("wN") || piece.equals("bN")) {
                    allPossibleMoves.addAll(knightLegalMove(position, piece, boardState));
                } else if (piece.equals("wQ") || piece.equals("bQ")) {
                    allPossibleMoves.addAll(queenLegalMove(position, piece, boardState));
                } else if (piece.equals("wK") || piece.equals("bK")) {
                    allPossibleMoves.addAll(kingLegalMove(position, piece, boardState));
                }
            }
        }   

        return allPossibleMoves;
    }

    public boolean checkCheckMate(String whosTurn, Map<String, String> boardState) {
        // Determine the enemy king and player colors
        String enemyKing = whosTurn.equals("white-turn") ? "bK" : "wK";
        String opponentColor = whosTurn.equals("white-turn") ? "white" : "black";
        String currentColor = whosTurn.equals("white-turn") ? "w" : "b";
    
        // Find the enemy king's position
        String kingPosition = null;
        for (Map.Entry<String, String> entry : boardState.entrySet()) {
            if (entry.getValue().equals(enemyKing)) {
                kingPosition = entry.getKey();
                break;
            }
        }
    
        // If the king is not on the board, it's not checkmate
        if (kingPosition == null) {
            return false;
        }
    
        // Check if the enemy king is currently in check
        if (!isSquareThreatened(kingPosition, currentColor, boardState)) {
            return false; // Not in check, so not checkmate
        }
    
        // Generate all legal moves for the enemy player
        String enemyTurn = whosTurn.equals("white-turn") ? "black-turn" : "white-turn";
        List<Map<String, String>> allEnemyMoves = legalMove(enemyTurn, boardState);
    
        // Simulate each move and check if the king can escape check
        for (Map<String, String> possibleMove : allEnemyMoves) {
            // Update the king's position for the current move
            String newKingPosition = null;
            for (Map.Entry<String, String> entry : possibleMove.entrySet()) {
                if (entry.getValue().equals(enemyKing)) {
                    newKingPosition = entry.getKey();
                    break;
                }
            }
    
            // Default to the original king's position if no new position is found
            if (newKingPosition == null) {
                newKingPosition = kingPosition;
            }
    
            // Check if the king is still in check after this move
            if (!isSquareThreatened(newKingPosition, currentColor, possibleMove)) {
                return false; // Not checkmate, as the king can escape check
            }
        }
    
        // If all possible moves leave the king in check, it is checkmate
        return true;
    }
    

    private List<Map<String, String>> generateNewBoardStates(String value, String position, List<String> legalMoves, Map<String, String> boardState) {
        List<Map<String, String>> newBoardStates = new ArrayList<>();

        for (String legalMove : legalMoves) {
            Map<String, String> newBoardState = new LinkedHashMap<>(boardState);

            newBoardState.put(position, "empty");
            newBoardState.put(legalMove, value);

            newBoardStates.add(newBoardState);
        }

        return newBoardStates;
    }

    private List<Map<String, String>> pawnLegalMove(String position, String value, Map<String, String> boardState) {
        int row = Character.getNumericValue(position.charAt(1));
        char col = position.charAt(0);
    
        List<String> legalMoves = new ArrayList<>();
        String statusMessage = "No capture";
    
        if (value.equals("wP")) {
            if (row < 8) {
                String forwardSquare = col + String.valueOf(row + 1);
                if (!boardState.containsKey(forwardSquare) || boardState.get(forwardSquare).equals("empty")) {
                    legalMoves.add(forwardSquare);
                }
    
                if (row == 2) {
                    String doubleForwardSquare = col + String.valueOf(row + 2);
                    String squareInBetween = col + String.valueOf(row + 1);
    
                    if ((boardState.containsKey(squareInBetween) && boardState.get(squareInBetween).equals("empty")) &&
                        (!boardState.containsKey(doubleForwardSquare) || boardState.get(doubleForwardSquare).equals("empty"))) {
                        legalMoves.add(doubleForwardSquare);
                    }
                }
    
                if (col > 'a') {
                    String diagonalLeft = (char)(col - 1) + String.valueOf(row + 1);
                    if (boardState.containsKey(diagonalLeft) && boardState.get(diagonalLeft).startsWith("b")) {
                        legalMoves.add(diagonalLeft);
                        statusMessage = "Capture possible on " + diagonalLeft;
                    }
                }
                if (col < 'h') {
                    String diagonalRight = (char)(col + 1) + String.valueOf(row + 1);
                    if (boardState.containsKey(diagonalRight) && boardState.get(diagonalRight).startsWith("b")) {
                        legalMoves.add(diagonalRight);
                        statusMessage = "Capture possible on " + diagonalRight;
                    }
                }
            }

        } else if (value.equals("bP")) {
            if (row > 1) {
                String forwardSquare = col + String.valueOf(row - 1);
                if (!boardState.containsKey(forwardSquare) || boardState.get(forwardSquare).equals("empty")) {
                    legalMoves.add(forwardSquare);
                }
    
                if (row == 7) {
                    String doubleForwardSquare = col + String.valueOf(row - 2);
                    String squareInBetween = col + String.valueOf(row - 1);
    
                    if ((boardState.containsKey(squareInBetween) && boardState.get(squareInBetween).equals("empty")) &&
                        (!boardState.containsKey(doubleForwardSquare) || boardState.get(doubleForwardSquare).equals("empty"))) {
                        legalMoves.add(doubleForwardSquare);
                    }
                }
    
                if (col > 'a') {
                    String diagonalLeft = (char)(col - 1) + String.valueOf(row - 1);
                    if (boardState.containsKey(diagonalLeft) && boardState.get(diagonalLeft).startsWith("w")) {
                        legalMoves.add(diagonalLeft);
                        statusMessage = "Capture possible on " + diagonalLeft;
                    }
                }
                if (col < 'h') {
                    String diagonalRight = (char)(col + 1) + String.valueOf(row - 1);
                    if (boardState.containsKey(diagonalRight) && boardState.get(diagonalRight).startsWith("w")) {
                        legalMoves.add(diagonalRight);
                        statusMessage = "Capture possible on " + diagonalRight;
                    }
                }
            }
        }
    
        return generateNewBoardStates(value, position, legalMoves, boardState);
    }
    
    private List<Map<String, String>> rookLegalMove(String position, String value, Map<String, String> boardState) {
        int row = Character.getNumericValue(position.charAt(1));
        char col = position.charAt(0);
        List<String> legalMoves = new ArrayList<>();
        String statusMessage = "No capture";
    
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
        for (int[] dir : directions) {
            for (int i = 1; i < 8; i++) {
                int newRow = row + (i * dir[0]);
                char newCol = (char)(col + (i * dir[1]));
    
                if (newRow >= 1 && newRow <= 8 && newCol >= 'a' && newCol <= 'h') {
                    String newPosition = newCol + String.valueOf(newRow);
    
                    if (!boardState.containsKey(newPosition) || boardState.get(newPosition).equals("empty")) {
                        legalMoves.add(newPosition);
                    } 
                    else if ((value.startsWith("w") && boardState.get(newPosition).startsWith("b")) || 
                             (value.startsWith("b") && boardState.get(newPosition).startsWith("w"))) {
                        legalMoves.add(newPosition);
                        statusMessage = "Capture possible on " + newPosition;
                        break;
                    } 
                    else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return generateNewBoardStates(value, position, legalMoves, boardState);
    }

    private List<Map<String, String>> bishopLegalMove(String position, String value, Map<String, String> boardState) {
        int row = Character.getNumericValue(position.charAt(1));
        char col = position.charAt(0);
        List<String> legalMoves = new ArrayList<>();
        String statusMessage = "No capture";
    
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        
        for (int[] dir : directions) {
            for (int i = 1; i < 8; i++) {
                int newRow = row + (i * dir[0]);
                char newCol = (char)(col + (i * dir[1]));
    
                if (newRow >= 1 && newRow <= 8 && newCol >= 'a' && newCol <= 'h') {
                    String newPosition = newCol + String.valueOf(newRow);
    
                    if (!boardState.containsKey(newPosition) || boardState.get(newPosition).equals("empty")) {
                        legalMoves.add(newPosition);
                    } 
                    else if ((value.startsWith("w") && boardState.get(newPosition).startsWith("b")) || 
                             (value.startsWith("b") && boardState.get(newPosition).startsWith("w"))) {
                        legalMoves.add(newPosition);
                        statusMessage = "Capture possible on " + newPosition;
                        break;
                    } 
                    else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    
        return generateNewBoardStates(value, position, legalMoves, boardState);
    }
    
    private List<Map<String, String>> knightLegalMove(String position, String value, Map<String, String> boardState) {
        int row = Character.getNumericValue(position.charAt(1));
        char col = position.charAt(0);
        List<String> legalMoves = new ArrayList<>();
        String statusMessage = "No capture";
    
        int[][] moves = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        
        for (int[] move : moves) {
            int newRow = row + move[0];
            char newCol = (char)(col + move[1]);
    
            if (newRow >= 1 && newRow <= 8 && newCol >= 'a' && newCol <= 'h') {
                String newPosition = newCol + String.valueOf(newRow);
    
                if (!boardState.containsKey(newPosition) || boardState.get(newPosition).equals("empty")) {
                    legalMoves.add(newPosition);
                } 
                else if ((value.startsWith("w") && boardState.get(newPosition).startsWith("b")) || 
                         (value.startsWith("b") && boardState.get(newPosition).startsWith("w"))) {
                    legalMoves.add(newPosition);
                    statusMessage = "Capture possible on " + newPosition;
                }
            }
        }
    
        return generateNewBoardStates(value, position, legalMoves, boardState);
    }

    private List<Map<String, String>> queenLegalMove(String position, String value, Map<String, String> boardState) {
        int row = Character.getNumericValue(position.charAt(1));
        char col = position.charAt(0);
        List<String> legalMoves = new ArrayList<>();
        String statusMessage = "No capture";
    
        int[][] directions = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };
        
        for (int[] dir : directions) {
            for (int i = 1; i < 8; i++) {
                int newRow = row + (i * dir[0]);
                char newCol = (char)(col + (i * dir[1]));
    
                if (newRow >= 1 && newRow <= 8 && newCol >= 'a' && newCol <= 'h') {
                    String newPosition = newCol + String.valueOf(newRow);
    
                    if (!boardState.containsKey(newPosition) || boardState.get(newPosition).equals("empty")) {
                        legalMoves.add(newPosition);
                    } 
                    else if ((value.startsWith("w") && boardState.get(newPosition).startsWith("b")) || 
                             (value.startsWith("b") && boardState.get(newPosition).startsWith("w"))) {
                        legalMoves.add(newPosition);
                        statusMessage = "Capture possible on " + newPosition;
                        break;
                    } 
                    else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    
        return generateNewBoardStates(value, position, legalMoves, boardState);
    }

    private List<Map<String, String>> kingLegalMove(String position, String value, Map<String, String> boardState) {
        int row = Character.getNumericValue(position.charAt(1));
        char col = position.charAt(0);
        List<String> legalMoves = new ArrayList<>();
        String statusMessage = "No capture";
    
        int[][] moves = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };
        
        String opponentColor = value.startsWith("w") ? "b" : "w";
        
        boardState.put(position, "empty");
        
        for (int[] move : moves) {
            
            int newRow = row + move[0];
            char newCol = (char)(col + move[1]);
    
            if (newRow >= 1 && newRow <= 8 && newCol >= 'a' && newCol <= 'h') {
                String newPosition = newCol + String.valueOf(newRow);
                Map<String, String> newBoardState = new HashMap<>(boardState);
                newBoardState.put(newPosition, value);
    
                if (isSquareThreatened(newPosition, opponentColor, newBoardState)) {
                    continue;
                }
    
                if (!boardState.containsKey(newPosition) || boardState.get(newPosition).equals("empty")) {
                    legalMoves.add(newPosition);
                } else if ((value.startsWith("w") && boardState.get(newPosition).startsWith("b")) || 
                           (value.startsWith("b") && boardState.get(newPosition).startsWith("w"))) {
                    legalMoves.add(newPosition);
                    statusMessage = "Capture possible on " + newPosition;
                }
            }
        }
    
        boardState.put(position, value);
    
        return generateNewBoardStates(value, position, legalMoves, boardState);
    }
    
    private boolean isSquareThreatened(String square, String opponentColor, Map<String, String> boardState) {

        for (Map.Entry<String, String> entry : boardState.entrySet()) {
            String piecePos = entry.getKey();
            String piece = entry.getValue();
    
            if (!piece.startsWith(opponentColor)) {
                continue;
            }
    
            if (isThreatening(piecePos, piece, square, boardState)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isThreatening(String piecePos, String piece, String targetSquare, Map<String, String> boardState) {
        switch (piece) {
            case "bP":
            case "wP":
                return isPawnThreatening(piecePos, piece, targetSquare);
            case "bR":
            case "wR":
                return isRookThreatening(piecePos, piece, targetSquare, boardState);
            case "bB":
            case "wB":
                return isBishopThreatening(piecePos, piece, targetSquare, boardState);
            case "bN":
            case "wN":
                return isKnightThreatening(piecePos, piece, targetSquare);
            case "bQ":
            case "wQ":
                return isQueenThreatening(piecePos, piece, targetSquare, boardState);
            case "bK":
            case "wK":
                return isKingThreatening(piecePos, piece, targetSquare);
            default:
                return false;
        }
    }    
    
    private boolean isPawnThreatening(String piecePos, String piece, String targetSquare) {
        int pawnRow = Character.getNumericValue(piecePos.charAt(1));
        char pawnCol = piecePos.charAt(0);
        int pawnDirection = piece.startsWith("w") ? 1 : -1;
    
        return (targetSquare.equals(String.valueOf((char)(pawnCol - 1)) + String.valueOf(pawnRow + pawnDirection)) ||
                targetSquare.equals(String.valueOf((char)(pawnCol + 1)) + String.valueOf(pawnRow + pawnDirection)));
    }
    
    private boolean isRookThreatening(String piecePos, String piece, String targetSquare, Map<String, String> boardState) {
        int rookRow = Character.getNumericValue(piecePos.charAt(1));
        char rookCol = piecePos.charAt(0);
        int targetRow = Character.getNumericValue(targetSquare.charAt(1));
        char targetCol = targetSquare.charAt(0);
    
        if (rookRow == targetRow || rookCol == targetCol) {
            return isPathClear(piecePos, targetSquare, boardState);
        }
        return false;
    }
    
    private boolean isBishopThreatening(String piecePos, String piece, String targetSquare, Map<String, String> boardState) {
        int bishopRow = Character.getNumericValue(piecePos.charAt(1));
        char bishopCol = piecePos.charAt(0);
        int targetRow = Character.getNumericValue(targetSquare.charAt(1));
        char targetCol = targetSquare.charAt(0);
    
        if (Math.abs(bishopRow - targetRow) == Math.abs(bishopCol - targetCol)) {
            return isPathClear(piecePos, targetSquare, boardState);
        }
        return false;
    }
    
    private boolean isKnightThreatening(String piecePos, String piece, String targetSquare) {
        int knightRow = Character.getNumericValue(piecePos.charAt(1));
        char knightCol = piecePos.charAt(0);
        int targetRow = Character.getNumericValue(targetSquare.charAt(1));
        char targetCol = targetSquare.charAt(0);
    
        int rowDiff = Math.abs(knightRow - targetRow);
        int colDiff = Math.abs(knightCol - targetCol);
    
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }
    
    private boolean isQueenThreatening(String piecePos, String piece, String targetSquare, Map<String, String> boardState) {
        return isRookThreatening(piecePos, piece, targetSquare, boardState) || 
               isBishopThreatening(piecePos, piece, targetSquare, boardState);
    }
    
    private boolean isKingThreatening(String piecePos, String piece, String targetSquare) {
        int kingRow = Character.getNumericValue(piecePos.charAt(1));
        char kingCol = piecePos.charAt(0);
        int targetRow = Character.getNumericValue(targetSquare.charAt(1));
        char targetCol = targetSquare.charAt(0);
    
        return (Math.abs(kingRow - targetRow) <= 1 && Math.abs(kingCol - targetCol) <= 1);
    }
    
    private boolean isPathClear(String startPos, String endPos, Map<String, String> boardState) {
        int startRow = Character.getNumericValue(startPos.charAt(1));
        char startCol = startPos.charAt(0);
        int endRow = Character.getNumericValue(endPos.charAt(1));
        char endCol = endPos.charAt(0);
    
        int rowStep = Integer.compare(endRow, startRow);
        int colStep = Integer.compare(endCol, startCol);
    
        int currentRow = startRow + rowStep;
        char currentCol = (char)(startCol + colStep);
    
        while (currentRow != endRow || currentCol != endCol) {
            String checkSquare = currentCol + String.valueOf(currentRow);
            if (boardState.containsKey(checkSquare) && !boardState.get(checkSquare).equals("empty")) {
                return false;
            }
            currentRow += rowStep;
            currentCol = (char)(currentCol + colStep);
        }
    
        return true;
    }
}
