import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int[][] boardTiles;
    private final int n;

    public Board(int[][] tiles) {
        n = tiles.length;
        boardTiles = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                boardTiles[row][col] = tiles[row][col];
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(boardTiles[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (boardTiles[i][j] == 0) continue;
                if (boardTiles[i][j] != i * n + j + 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = boardTiles[i][j];
                if (value != 0) {
                    int goalRow = (value - 1) / n;
                    int goalCol = (value - 1) % n;
                    sum += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return sum;
    }

    public boolean isGoal() {
        if (boardTiles[n - 1][n - 1] != 0) return false;
        
        int expectedValue = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n - 1 && j == n - 1) break;
                if (boardTiles[i][j] != expectedValue++) return false;
            }
        }
        return true;
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        
        Board other = (Board) y;
        if (n != other.n) return false;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (boardTiles[i][j] != other.boardTiles[i][j]) return false;
            }
        }
        return true;
    }

    private int[][] copyBoard() {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(boardTiles[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    private Board createNeighbor(int[][] neighbor, int i1, int j1, int i2, int j2) {
        int temp = neighbor[i1][j1];
        neighbor[i1][j1] = neighbor[i2][j2];
        neighbor[i2][j2] = temp;
        return new Board(neighbor);
    }

    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>(4); // Pre-size to max possible neighbors
        int zeroRow = -1, zeroCol = -1;
        
        // Find empty tile - scan from end since 0 is often there
        for (int i = n-1; i >= 0 && zeroRow == -1; i--) {
            for (int j = n-1; j >= 0; j--) {
                if (boardTiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    break;
                }
            }
        }

        // Create single copy of board for all neighbors
        
        // Add possible moves
        if (zeroRow > 0) neighbors.add(createNeighbor(copyBoard(), zeroRow, zeroCol, zeroRow - 1, zeroCol));
        if (zeroRow < n - 1) neighbors.add(createNeighbor(copyBoard(), zeroRow, zeroCol, zeroRow + 1, zeroCol));
        if (zeroCol > 0) neighbors.add(createNeighbor(copyBoard(), zeroRow, zeroCol, zeroRow, zeroCol - 1));
        if (zeroCol < n - 1) neighbors.add(createNeighbor(copyBoard(), zeroRow, zeroCol, zeroRow, zeroCol + 1));

        return neighbors;
    }

    public Board twin() {
        int[][] twin = copyBoard();
        
        // Find first pair of adjacent non-zero tiles
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (twin[i][j] != 0 && twin[i][j + 1] != 0) {
                    int temp = twin[i][j];
                    twin[i][j] = twin[i][j + 1];
                    twin[i][j + 1] = temp;
                    return new Board(twin);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(tiles);
        
        StdOut.println("Board:\n" + board);
        StdOut.println("Dimension: " + board.dimension());
        StdOut.println("Hamming distance: " + board.hamming());
        StdOut.println("Manhattan distance: " + board.manhattan());
        StdOut.println("Is goal board? " + board.isGoal());
        
        Board sameBoard = new Board(tiles);
        Board differentBoard = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}});
        StdOut.println("Equals same board? " + board.equals(sameBoard));
        StdOut.println("Equals different board? " + board.equals(differentBoard));
    }
}