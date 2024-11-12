import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solver {
    // private static final int CACHE_LIMIT = 100000;
    // cache manhattan distance limit to 100000
    // private int getManhattanDistance(Board board) {
    //     String boardString = board.toString();
    //     if (manhattanCache.containsKey(boardString)) {
    //         return manhattanCache.get(boardString);
    //     }
    //     int manhattanDistance = board.manhattan();
    //     manhattanCache.put(boardString, manhattanDistance);
    //     return manhattanDistance;
    // }

    // private Map<String, Integer> manhattanCache = new HashMap<>(CACHE_LIMIT);
    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        SearchNode parent;
        int moves;
        int manhattanDistance;

        public SearchNode(Board board, SearchNode parent, int moves) {
            this.board = board;
            this.parent = parent;
            this.moves = moves;
            this.manhattanDistance = board.manhattan();
        }

        @Override
        public int compareTo(SearchNode other) {
            return Integer.compare(this.manhattanDistance + this.moves, other.manhattanDistance + other.moves);
        }
    }

    private Boolean isSolvable = false;
    private SearchNode endNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Initial board cannot be null");
        }
        
        Board twinBoard = initial.twin();
        if (twinBoard == null) {
            throw new IllegalArgumentException("Twin board cannot be null");
        }
        
        SearchNode searchNode = new SearchNode(initial, null, 0); // initial board, null parent, 0 moves
        SearchNode twinSearchNode = new SearchNode(twinBoard, null, 0); // twin board, null parent, 0 moves
        MinPQ<SearchNode> priorityQueue = new MinPQ<>();
        MinPQ<SearchNode> twinPriorityQueue = new MinPQ<>();

        priorityQueue.insert(searchNode);
        twinPriorityQueue.insert(twinSearchNode);

        Map<String, Boolean> visited = new HashMap<>();
        visited.put(initial.toString(), true);

        Map<String, Boolean> twinVisited = new HashMap<>();
        twinVisited.put(twinBoard.toString(), true);
    
        Boolean solved = false;
        Boolean twinSolved = false;
        while (!solved && !twinSolved) {
            SearchNode current = priorityQueue.delMin();
            SearchNode twinCurrent = twinPriorityQueue.delMin();
            Board currentBoard = current.board;
            Board twinCurrentBoard = twinCurrent.board;
            
            if (currentBoard.isGoal()) {
                isSolvable = true;
                endNode = current;
                solved = true;
                break;
            }

            if (twinCurrentBoard.isGoal()) {
                isSolvable = false;
                twinSolved = true;
                break;
            }

            for (Board neighbor : currentBoard.neighbors()) {
                String neighborString = neighbor.toString();
                if ((current.parent == null || !neighbor.equals(current.parent.board)) && !visited.containsKey(neighborString)) {
                    visited.put(neighborString, true); 
                    SearchNode neighborNode = new SearchNode(neighbor, current, current.moves + 1);
                    priorityQueue.insert(neighborNode);
                }
            }

            for (Board neighbor : twinCurrentBoard.neighbors()) {
                String neighborString = neighbor.toString();
                if ((twinCurrent.parent == null || !neighbor.equals(twinCurrent.parent.board)) && !twinVisited.containsKey(neighborString)) {
                    twinVisited.put(neighborString, true);
                    SearchNode neighborNode = new SearchNode(neighbor, twinCurrent, twinCurrent.moves + 1);
                    twinPriorityQueue.insert(neighborNode);
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;

    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable || endNode == null) {
            return -1;
        }
        return endNode.moves;

    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable || endNode == null) {
            return null;
        }
        List<Board> solutionList = new ArrayList<>();
        SearchNode current = endNode;
        while (current.parent != null) {
            solutionList.add(current.board);
            current = current.parent;
        }
        solutionList.add(current.board);
        // reverse the solution list
        List<Board> reverseSolutionList = new ArrayList<>();
        for (int i = solutionList.size() - 1; i >= 0; i--) {
            reverseSolutionList.add(solutionList.get(i));
        }
        return reverseSolutionList;
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