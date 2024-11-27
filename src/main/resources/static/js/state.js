
const empty_board = {
    "1-1": "empty", "1-2": "empty", "1-3": "empty", "1-4": "empty", "1-5": "empty", "1-6": "empty", "1-7": "empty", "1-8": "empty",
    "2-1": "empty", "2-2": "empty", "2-3": "empty", "2-4": "empty", "2-5": "empty", "2-6": "empty", "2-7": "empty", "2-8": "empty",
    "3-1": "empty", "3-2": "empty", "3-3": "empty", "3-4": "empty", "3-5": "empty", "3-6": "empty", "3-7": "empty", "3-8": "empty",
    "4-1": "empty", "4-2": "empty", "4-3": "empty", "4-4": "empty", "4-5": "empty", "4-6": "empty", "4-7": "empty", "4-8": "empty",
    "5-1": "empty", "5-2": "empty", "5-3": "empty", "5-4": "empty", "5-5": "empty", "5-6": "empty", "5-7": "empty", "5-8": "empty",
    "6-1": "empty", "6-2": "empty", "6-3": "empty", "6-4": "empty", "6-5": "empty", "6-6": "empty", "6-7": "empty", "6-8": "empty",
    "7-1": "empty", "7-2": "empty", "7-3": "empty", "7-4": "empty", "7-5": "empty", "7-6": "empty", "7-7": "empty", "7-8": "empty",
    "8-1": "empty", "8-2": "empty", "8-3": "empty", "8-4": "empty", "8-5": "empty", "8-6": "empty", "8-7": "empty", "8-8": "empty"
}

const initial_board = {
    "1-1": "bR", "1-2": "bN", "1-3": "bB", "1-4": "bQ", "1-5": "bK", "1-6": "bB", "1-7": "bN", "1-8": "bR", // Black pieces
    "2-1": "bP", "2-2": "bP", "2-3": "bP", "2-4": "bP", "2-5": "bP", "2-6": "bP", "2-7": "bP", "2-8": "bP", // Black pawns
    "3-1": "empty", "3-2": "empty", "3-3": "empty", "3-4": "empty", "3-5": "empty", "3-6": "empty", "3-7": "empty", "3-8": "empty", // Empty row
    "4-1": "empty", "4-2": "empty", "4-3": "empty", "4-4": "empty", "4-5": "empty", "4-6": "empty", "4-7": "empty", "4-8": "empty", // Empty row
    "5-1": "empty", "5-2": "empty", "5-3": "empty", "5-4": "empty", "5-5": "empty", "5-6": "empty", "5-7": "empty", "5-8": "empty", // Empty row
    "6-1": "empty", "6-2": "empty", "6-3": "empty", "6-4": "empty", "6-5": "empty", "6-6": "empty", "6-7": "empty", "6-8": "empty", // Empty row
    "7-1": "wP", "7-2": "wP", "7-3": "wP", "7-4": "wP", "7-5": "wP", "7-6": "wP", "7-7": "wP", "7-8": "wP", // White pawns
    "8-1": "wR", "8-2": "wN", "8-3": "wB", "8-4": "wQ", "8-5": "wK", "8-6": "wB", "8-7": "wN", "8-8": "wR"  // White pieces
};

const reverse_board = {
    "1-1": "wR", "1-2": "wN", "1-3": "wB", "1-4": "wQ", "1-5": "wK", "1-6": "wB", "1-7": "wN", "1-8": "wR", // White pieces in black's back row
    "2-1": "wP", "2-2": "wP", "2-3": "wP", "2-4": "wP", "2-5": "wP", "2-6": "wP", "2-7": "wP", "2-8": "wP", // White pawns in black's row
    "3-1": "empty", "3-2": "empty", "3-3": "empty", "3-4": "empty", "3-5": "empty", "3-6": "empty", "3-7": "empty", "3-8": "empty", // Empty row
    "4-1": "empty", "4-2": "empty", "4-3": "empty", "4-4": "empty", "4-5": "empty", "4-6": "empty", "4-7": "empty", "4-8": "empty", // Empty row
    "5-1": "empty", "5-2": "empty", "5-3": "empty", "5-4": "empty", "5-5": "empty", "5-6": "empty", "5-7": "empty", "5-8": "empty", // Empty row
    "6-1": "empty", "6-2": "empty", "6-3": "empty", "6-4": "empty", "6-5": "empty", "6-6": "empty", "6-7": "empty", "6-8": "empty", // Empty row
    "7-1": "bP", "7-2": "bP", "7-3": "bP", "7-4": "bP", "7-5": "bP", "7-6": "bP", "7-7": "bP", "7-8": "bP", // Black pawns in white's row
    "8-1": "bR", "8-2": "bN", "8-3": "bB", "8-4": "bQ", "8-5": "bK", "8-6": "bB", "8-7": "bN", "8-8": "bR"  // Black pieces in white's back row
};