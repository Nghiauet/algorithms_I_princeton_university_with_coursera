import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public int[][] boardTiles;
    private int n;
    public Board(int[][] tiles){
        n = tiles.length;
        this.boardTiles = new int[n][n];
        for (int row=0;row<n;row++){
            for (int col=0 ; col<n; col++){
                this.boardTiles[row][col] = tiles[row][col];
            }
        }
    }

    // string representation of this board
    public String toString() {
        String stringRepresentation = String.valueOf(n) + "\n";
        for (int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                stringRepresentation = stringRepresentation +  String.valueOf(boardTiles[i][j]) +" ";
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
        return 0;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (this.boardTiles[n-1][n-1] != 0){
            return false;
        }
        for (int i=0; i<n; i++){
            for (int j=0; j<n; j++){
                if ((i==(n-1)) && j==(n-1)){
                    return true;
                }
                else if (boardTiles[i][j] != (i+1)*n + j +1 ){
                    return false;
                }

            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        Board other = (Board) y;
        for (int i=0; i<n; i++){
            for (int j=0; j<n; j++){

                if (this.boardTiles[i][j] != other.boardTiles[i][j]){
                    return false;
                }

            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        StdOut.println("hello woulds");
    }

}