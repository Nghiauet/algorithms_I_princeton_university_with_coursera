import java.util.ArrayList;
import java.util.List;
public class BoardTest {

    // Helper method to print test result
    public static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println(testName + " passed");
        } else {
            System.out.println(testName + " failed");
        }
    }

    // Test case for the constructor and toString method
    public static void testConstructorAndToString() {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(tiles);

        String expectedString = "3\n1 2 3 \n4 5 6 \n7 8 0 \n";
        assertTrue("testConstructorAndToString", board.toString().equals(expectedString));
    }

    // Test case for the dimension method
    public static void testDimension() {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(tiles);
        assertTrue("testDimension", board.dimension() == 3);

        int[][] largerTiles = {{1, 2}, {3, 0}};
        Board largerBoard = new Board(largerTiles);
        assertTrue("testDimension with 2x2 board", largerBoard.dimension() == 2);
    }

    // Test case for the hamming method
    public static void testHamming() {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(tiles);
        assertTrue("testHamming (goal board)", board.hamming() == 0);

        int[][] notGoalTiles = {{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};
        Board notGoalBoard = new Board(notGoalTiles);
        assertTrue("testHamming (non-goal board)", notGoalBoard.hamming() > 0);
    }

    // Test case for the manhattan method
    public static void testManhattan() {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(tiles);
        assertTrue("testManhattan (goal board)", board.manhattan() == 0);

        int[][] notGoalTiles = {{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};
        Board notGoalBoard = new Board(notGoalTiles);
        assertTrue("testManhattan (non-goal board)", notGoalBoard.manhattan() > 0);
    }

    // Test case for the isGoal method
    public static void testIsGoal() {
        int[][] goalTiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board goalBoard = new Board(goalTiles);
        assertTrue("testIsGoal (goal board)", goalBoard.isGoal());

        int[][] notGoalTiles = {{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};
        Board notGoalBoard = new Board(notGoalTiles);
        assertTrue("testIsGoal (non-goal board)", !notGoalBoard.isGoal());
    }

    // Test case for the equals method
    public static void testEquals() {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board1 = new Board(tiles);
        Board board2 = new Board(tiles);
        assertTrue("testEquals (same boards)", board1.equals(board2));

        int[][] differentTiles = {{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};
        Board differentBoard = new Board(differentTiles);
        assertTrue("testEquals (different boards)", !board1.equals(differentBoard));

        // Test with null
        assertTrue("testEquals (null)", !board1.equals(null));

        // Test with object of different class
        assertTrue("testEquals (different class)", !board1.equals("string"));
    }
    // Test case for the neighbors method
    public static void testNeighbors() {
        int[][] tiles = {{1, 2, 3}, {4, 0, 5}, {6, 7, 8}};  // The empty tile (0) is at position (1,1)
        Board board = new Board(tiles);

        List<Board> neighborsList = new ArrayList<>();
        for (Board neighbor : board.neighbors()) {
            neighborsList.add(neighbor);
        }

        // We expect 4 possible neighbors by moving the empty tile (up, down, left, right)
        // Neighbor 1: Move 0 up (swap with 4)
        int[][] expectedNeighbor1 = {{1, 0, 3}, {4, 2, 5}, {6, 7, 8}};
        // Neighbor 2: Move 0 down (swap with 7)
        int[][] expectedNeighbor2 = {{1, 2, 3}, {4, 7, 5}, {6, 0, 8}};
        // Neighbor 3: Move 0 left (swap with 4)
        int[][] expectedNeighbor3 = {{1, 2, 3}, {0, 4, 5}, {6, 7, 8}};
        // Neighbor 4: Move 0 right (swap with 5)
        int[][] expectedNeighbor4 = {{1, 2, 3}, {4, 5, 0}, {6, 7, 8}};

        List<Board> expectedNeighbors = new ArrayList<>();
        expectedNeighbors.add(new Board(expectedNeighbor1));
        expectedNeighbors.add(new Board(expectedNeighbor2));
        expectedNeighbors.add(new Board(expectedNeighbor3));
        expectedNeighbors.add(new Board(expectedNeighbor4));

        // Assert that we found exactly 4 neighbors
        assertTrue("testNeighbors: correct number of neighbors", neighborsList.size() == 4);

        // Assert that the generated neighbors match the expected neighbors

        // for (Board neighbor : neighborsList) {
        //     String temp = neighbor.toString();
        //     System.out.println("neighbor board: " + temp);
        //     }
        // for (Board neighbor : expectedNeighbors) {
        //     String temp = neighbor.toString();
        //     System.out.println("expectedNeighbors board: " + temp);
        //     }
        for (Board expectedBoard : expectedNeighbors) {
            boolean foundMatch = false;
            for (Board neighbor : neighborsList) {
                if (neighbor.equals(expectedBoard)) {
                    foundMatch = true;
                    break;
                }
            }
            assertTrue("testNeighbors: neighbor matches expected", foundMatch);
        }
    }
    // Additional test cases (see previous code for deeper tests like null/empty boards, deep copy, etc.)


    // Test case for the twin method
    public static void testTwin() {
        // Basic case: Test with a normal board
        int[][] tiles = {
            {1, 2, 3},
            {4, 0, 5},
            {6, 7, 8}
        };
        Board board = new Board(tiles);
        Board twinBoard = board.twin();
        
        // Ensure the twin is not equal to the original
        assertTrue("testTwin: twin not equal to original", !board.equals(twinBoard));

        // Check if the twin swap is valid (non-zero tiles swapped)
        boolean isTwinValid = (twinBoard.boardTiles[0][0] == 2 && twinBoard.boardTiles[0][1] == 1) ||
                                (twinBoard.boardTiles[1][0] == 4 && twinBoard.boardTiles[1][1] == 5);
        assertTrue("testTwin: valid twin created (swapped non-zero tiles)", isTwinValid);

        // Edge case 1: Test with the empty tile in the top-left corner
        int[][] cornerTiles = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8}
        };
        Board cornerBoard = new Board(cornerTiles);
        Board cornerTwin = cornerBoard.twin();
        assertTrue("testTwin: corner twin not equal to original", !cornerBoard.equals(cornerTwin));

        // Check if the twin swap is valid in this case as well
        isTwinValid = (cornerTwin.boardTiles[0][1] == 2 && cornerTwin.boardTiles[0][2] == 1);
        assertTrue("testTwin: valid twin created (corner case)", isTwinValid);

        // Edge case 2: Test with the empty tile in the bottom-right corner
        int[][] bottomRightCornerTiles = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        Board bottomRightCornerBoard = new Board(bottomRightCornerTiles);
        Board bottomRightCornerTwin = bottomRightCornerBoard.twin();
        assertTrue("testTwin: bottom-right corner twin not equal to original", !bottomRightCornerBoard.equals(bottomRightCornerTwin));

        // Check if the twin swap is valid in this case
        isTwinValid = (bottomRightCornerTwin.boardTiles[0][0] == 2 && bottomRightCornerTwin.boardTiles[0][1] == 1);
        assertTrue("testTwin: valid twin created (bottom-right corner case)", isTwinValid);

        // Edge case 3: Test a board with only non-zero tiles and no space (this can still generate a valid twin)
        int[][] noZeroTiles = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        Board noZeroBoard = new Board(noZeroTiles);
        Board noZeroTwin = noZeroBoard.twin();
        assertTrue("testTwin: no-zero twin not equal to original", !noZeroBoard.equals(noZeroTwin));

        // Check if the twin swap is valid for non-zero-only tiles
        isTwinValid = (noZeroTwin.boardTiles[0][0] == 2 && noZeroTwin.boardTiles[0][1] == 1);
        assertTrue("testTwin: valid twin created (non-zero tiles)", isTwinValid);
    }
    // Main method to run all the test cases
    public static void main(String[] args) {
        System.out.println("Running BoardTest...");

        // testConstructorAndToString();
        // testDimension();
        // testHamming();
        // testManhattan();
        // testIsGoal();
        // testEquals();

        // Add any new tests here, if necessary
        // testNeighbors();  // Run the test for neighbors
        testTwin();  // Run the test for twin

        System.out.println("All tests finished.");

        System.out.println("All tests finished.");
    }
}
