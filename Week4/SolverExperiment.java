import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class SolverExperiment {
    // Inner class representing a node in the A* search tree
    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;          // Current board state
        private final SearchNode parent;    // Parent node in search path
        private final int moves;            // Number of moves to reach this state
        private final int manhattanDistance; // Manhattan distance heuristic

        public SearchNode(Board board, SearchNode parent, int moves) {
            this.board = board;
            this.parent = parent;
            this.moves = moves;
            this.manhattanDistance = board.manhattan();
        }

        @Override
        public int compareTo(SearchNode other) {
            // Priority function: f(n) = g(n) + h(n)
            // where g(n) is moves made and h(n) is manhattan distance
            return Integer.compare(this.manhattanDistance + this.moves, 
                                 other.manhattanDistance + other.moves);
        }
    }

    private final boolean isSolvable;  // Whether puzzle is solvable
    private final SearchNode endNode;  // Final solution node if solved

    public SolverExperiment(Board initial) {
        // Validate input
        if (initial == null) {
            throw new IllegalArgumentException("Initial board cannot be null");
        }
        
        // Get twin board for parallel solving
        Board twinBoard = initial.twin();
        if (twinBoard == null) {
            throw new IllegalArgumentException("Twin board cannot be null");
        }
        
        // Initialize search nodes and priority queues for both original and twin boards
        SearchNode searchNode = new SearchNode(initial, null, 0);
        SearchNode twinSearchNode = new SearchNode(twinBoard, null, 0);
        MinPQ<SearchNode> priorityQueue = new MinPQ<>();
        MinPQ<SearchNode> twinPriorityQueue = new MinPQ<>();

        // Insert initial nodes
        priorityQueue.insert(searchNode);
        twinPriorityQueue.insert(twinSearchNode);
    
        SearchNode finalNode = null;
        boolean solved = false;
        boolean twinSolved = false;

        // Main A* search loop - continues until either original or twin is solved
        while (!solved && !twinSolved) {
            SearchNode current = priorityQueue.delMin();
            SearchNode twinCurrent = twinPriorityQueue.delMin();
            
            // Check if current state is goal state
            if (current.board.isGoal()) {
                finalNode = current;
                solved = true;
                break;
            }

            // Check if twin reached goal (means original is unsolvable)
            if (twinCurrent.board.isGoal()) {
                twinSolved = true;
                break;
            }

            // Process neighbors of current board
            for (Board neighbor : current.board.neighbors()) {
                // Skip if neighbor is parent
                if (current.parent == null || !neighbor.equals(current.parent.board)) {
                    priorityQueue.insert(new SearchNode(neighbor, current, current.moves + 1));
                }
            }

            // Process neighbors of twin board
            for (Board neighbor : twinCurrent.board.neighbors()) {
                if (twinCurrent.parent == null || !neighbor.equals(twinCurrent.parent.board)) {
                    twinPriorityQueue.insert(new SearchNode(neighbor, twinCurrent, twinCurrent.moves + 1));
                }
            }
        }
        
        this.isSolvable = solved;
        this.endNode = finalNode;
    }

    // Returns whether the puzzle is solvable
    public boolean isSolvable() {
        return isSolvable;
    }

    // Returns minimum number of moves to solve puzzle (-1 if unsolvable)
    public int moves() {
        if (!isSolvable || endNode == null) {
            return -1;
        }
        return endNode.moves;
    }

    // Returns sequence of boards in solution
    public Iterable<Board> solution() {
        if (!isSolvable || endNode == null) {
            return null;
        }
        
        // Reconstruct path from end node to initial board
        List<Board> solutionList = new ArrayList<>();
        SearchNode current = endNode;
        while (current != null) {
            solutionList.add(0, current.board);
            current = current.parent;
        }
        return solutionList;
    }

    // Test client
    public static void main(String[] args) {
        // Read puzzle from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);

        // Solve puzzle and print solution
        Solver solver = new Solver(initial);

        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}