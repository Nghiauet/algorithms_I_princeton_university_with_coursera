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

    // Additional test cases (see previous code for deeper tests like null/empty boards, deep copy, etc.)

    // Main method to run all the test cases
    public static void main(String[] args) {
        System.out.println("Running BoardTest...");

        testConstructorAndToString();
        testDimension();
        testHamming();
        testManhattan();
        testIsGoal();
        testEquals();

        // Add any new tests here, if necessary

        System.out.println("All tests finished.");
    }
}
