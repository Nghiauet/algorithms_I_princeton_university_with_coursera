import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.List;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public int[][] boardTiles;
    private int n;

    public Board(int[][] tiles) {
        n = tiles.length;
        this.boardTiles = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                this.boardTiles[row][col] = tiles[row][col];
            }
        }
    }

    // string representation of this board
    public String toString() {
        String stringRepresentation = String.valueOf(n) + "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stringRepresentation = stringRepresentation + String.valueOf(boardTiles[i][j]) + " ";
            }
            stringRepresentation = stringRepresentation + "\n";
        }
        return stringRepresentation;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == (n - 1)) && j == (n - 1)) {
                    if (this.boardTiles[i][j] != 0) {
                        count++;
                    }
                } else if (boardTiles[i][j] != (i) * n + j + 1) {
                    count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    private int manhattanDistance(int row, int col, int Value) {
        int goalRow = (Value - 1) / n;
        int goalCol = (Value - 1) % n;
        return Math.abs(row - goalRow) + Math.abs(col - goalCol);
    }

    public int manhattan() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == (n - 1)) && j == (n - 1)) {
                    if (this.boardTiles[i][j] != 0) {
                        count += manhattanDistance(i, j, n * n);
                    }
                } else if (boardTiles[i][j] != (i) * n + j + 1) {
                    count += manhattanDistance(i, j, boardTiles[i][j]);
                }
            }
        }
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (this.boardTiles[n - 1][n - 1] != 0) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == (n - 1)) && j == (n - 1)) {

                    return true;
                } else if (boardTiles[i][j] != (i) * n + j + 1) {

                    return false;
                }

            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }

        if (y == null || !(y instanceof Board)) {
            return false;
        }
        Board other = (Board) y;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (this.boardTiles[i][j] != other.boardTiles[i][j]) {
                    return false;
                }

            }
        }
        return true;
    }

    private int[][] copyBoard(int[][] board) {
        int[][] copyBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copyBoard[i][j] = board[i][j];
            }
        }
        return copyBoard;
    }

    public int[][] swap(int[][] board, int i, int j, int swap_i, int swap_j) {
        int[][] temp = copyBoard(board);
        int temp_value = temp[i][j];
        temp[i][j] = temp[swap_i][swap_j];
        temp[swap_i][swap_j] = temp_value;
        return temp;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int zero_i = -1;
        int zero_j = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.boardTiles[i][j] == 0) {
                    zero_i = i;
                    zero_j = j;
                    break;
                }
            }
        }
        // if (zero_i == -1) || (zero_j == -1){
        // return null;
        // }

        List<Board> neighbors = new ArrayList<>();
        // System.out.println("this board: " + this.toString());

        if (zero_i > 0) {
            int[][] newBoard = swap(this.boardTiles, zero_i, zero_j, zero_i - 1, zero_j);
            // System.out.println("possition: " + zero_i + " " +  zero_j + " " + zero_i + " " + zero_j );

            neighbors.add(new Board(newBoard));
            // System.out.println("newBoard board: " + new Board(newBoard).toString());
        }
        if (zero_j > 0) {
            int[][] newBoard = swap(this.boardTiles, zero_i, zero_j, zero_i, zero_j -1 );
            neighbors.add(new Board(newBoard));
            // System.out.println("newBoard board: " + new Board(newBoard).toString());

        }
        if (zero_i < n - 1) {
            int[][] newBoard = swap(this.boardTiles, zero_i, zero_j, zero_i+1, zero_j);
            neighbors.add(new Board(newBoard));
            // System.out.println("newBoard board: " + new Board(newBoard).toString());

        }
        if (zero_j < n - 1) {

            int[][] newBoard = swap(this.boardTiles, zero_i, zero_j, zero_i , zero_j+1);
            neighbors.add(new Board(newBoard));
            // System.out.println("newBoard board: " + new Board(newBoard).toString());

        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twin = copyBoard(this.boardTiles);
        // System.out.println("orignal : "+ new Board(twin).toString());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (twin[i][j] != 0 && (j<n-1) && twin[i][j+1] != 0) {
                    twin = swap(twin, i, j, i, j+1);
                    // System.out.println("twin : " + new Board(twin).toString());

                    return new Board(twin);
                }
                else if (twin[i][j] != 0 && (i<n-1) && twin[i+1][j] != 0) {
                    twin = swap(twin, i, j, i+1, j);
                    // System.out.println("twin : " + new Board(twin).toString());

                    return new Board(twin);
                }
                else if (twin[i][j] != 0 && (i>0) && twin[i-1][j] != 0) {
                    twin = swap(twin, i, j, i-1, j);
                    // System.out.println("twin : " + new Board(twin).toString());

                    return new Board(twin);
                }
                else if (twin[i][j] != 0 && (j>0) && twin[i][j-1] != 0) {
                    twin = swap(twin, i, j, i, j-1);
                    // System.out.println("twin : " + new Board(twin).toString());

                    return new Board(twin);
                }            }
        }
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // Test constructor and toString
        int[][] tiles = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        Board board = new Board(tiles);
        StdOut.println("Board:\n" + board.toString());

        // Test dimension
        StdOut.println("Dimension: " + board.dimension());

        // Test hamming
        StdOut.println("Hamming distance: " + board.hamming());

        // Test manhattan
        StdOut.println("Manhattan distance: " + board.manhattan());

        // Test isGoal
        StdOut.println("Is goal board? " + board.isGoal());

        // Test equals
        Board sameBoard = new Board(tiles);
        Board differentBoard = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 } });
        StdOut.println("Equals same board? " + board.equals(sameBoard));
        StdOut.println("Equals different board? " + board.equals(differentBoard));

        // // Test neighbors
        // StdOut.println("Neighbors:");
        // for (Board neighbor : board.neighbors()) {
        // StdOut.println(neighbor);
        // }

        // Test twin
        // StdOut.println("Twin:\n" + board.twin());
    }

}