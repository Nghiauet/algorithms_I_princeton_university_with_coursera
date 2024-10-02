public cla testSwap() {
    // Sample 3x3 board
    int[][] board = {
        {1, 2, 3},
        {4, 0, 5},
        {6, 7, 8}
    };

    Board b = new Board(board);

    // Test swapping (1, 1) (which is 0) with (2, 1) (which is 7)
    int[][] swappedBoard = b.swap(board, 1, 1, 2, 1);

    // Expected board after swap (should swap 0 and 7)
    int[][] expectedBoard = {
        {1, 2, 3},
        {4, 7, 5},
        {6, 0, 8}
    };

    // Check if the swappedBoard matches expectedBoard
    boolean isCorrect = true;
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[i].length; j++) {
            if (swappedBoard[i][j] != expectedBoard[i][j]) {
                isCorrect = false;
            }
        }
    }

    assertTrue("testSwap", isCorrect);
}
