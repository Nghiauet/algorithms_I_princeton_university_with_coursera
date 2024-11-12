import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Out;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

public class Solver {
    
    Comparator<Board> boardComparator = new Comparator<Board>() {
        @Override
        public int compare(Board b1, Board b2) {

            int f1 = b1.manhattan() + mapMovesCount.get(b1.toString());
            int f2 = b2.manhattan() + mapMovesCount.get(b2.toString());
            return Integer.compare(f1, f2);
        }
    };
    
    private int movesCount = 0;
    List<Board> solutionTracking = new ArrayList<>();
    private Map<String, Integer> mapMovesCount = new HashMap<>();
    private Boolean isSolvable = false;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // PQ
        // add the node in the PQ
        // int moveMins = 99999;
        // Board resultBoard = null;
        MinPQ<Board> priorityQueue = new MinPQ<>(boardComparator);
        Iterable<Board> neighbors = new ArrayList<>();
        // priorityQueue.insert(initial);

        Map<String, Board> camefrom = new HashMap<>();

        Set<String> checkDuplicate = new HashSet<>();
        Board b = initial;
        mapMovesCount.put(b.toString(),1);
        priorityQueue.insert(b);
        while (!priorityQueue.isEmpty() ) {
            // print all the values in the priority queue
            // for (Board board : priorityQueue) {
            //     StdOut.println("board : "+ board.toString());
            // }
            b = priorityQueue.delMin();
            // StdOut.println("size of priority queue "+ priorityQueue.size());
            if (b.isGoal()){
                solutionTracking.add(b);
                movesCount = 1;
                isSolvable = true;
                break;
            }
            neighbors = b.neighbors();
            // StdOut.println(b.toString());

            for (Board neighbor : neighbors) {
                if (!checkDuplicate.contains(neighbor.toString())) {
                    // StdOut.println("neighbor \n" + neighbor.toString());


                    camefrom.put(neighbor.toString(), b);
                    mapMovesCount.put(neighbor.toString(), mapMovesCount.get(b.toString())+1);
                    // StdOut.println("mapMovesCount.get(neighbor) :"+ mapMovesCount.get(neighbor.toString()));
                    // StdOut.println("mapMovesCount.get(b) :"+ mapMovesCount.get(neighbor));
                    // StdOut.println("mahatan distance :"+ neighbor.manhattan());
                    priorityQueue.insert(neighbor);


                    checkDuplicate.add(neighbor.toString());
                    // StdOut.println("neighbor : " + neighbor.toString());
                }
            }
            // StdOut.println("side "+  priorityQueue.size());

        }

        // StdOut.println("done");
        // solutionTracking.add(resultBoard);
        movesCount  =0;
        if (isSolvable){
            Board backTrack = b;
            while (!backTrack.toString().equals(initial.toString())){
            backTrack = camefrom.get(backTrack.toString());
            solutionTracking.add(backTrack);
                movesCount+= 1;
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;

    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return movesCount;

    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        return solutionTracking;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}