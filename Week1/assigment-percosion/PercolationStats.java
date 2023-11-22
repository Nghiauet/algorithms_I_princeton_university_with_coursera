import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double Z = 1.96;
    private int trials;
    private double[] resultsList;

    public PercolationStats(int n, int trials) {
        if (n < 1) {
            throw new IllegalArgumentException("Grid must have at least one row and column");
        }
        if (trials < 1) {
            throw new IllegalArgumentException("Number of trials must be at least one");
        }
        this.trials = trials;
        this.resultsList = new double[trials];
        for (int k = 0; k < trials; k++) {
            Percolation per = new Percolation(n);

            while (!per.percolates()) {
                int i = StdRandom.uniformInt(1, n + 1);
                int j = StdRandom.uniformInt(1, n + 1);
                // StdOut.println("i: " + i + ", j: " + j);
                if (!per.isOpen(i, j))
                    per.open(i, j);
            }
            resultsList[k] = (double) per.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(resultsList);
    }

    public double stddev() {
        return StdStats.stddev(resultsList);
    }

    public double confidenceLo() {
        return mean() - (Z * stddev() / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + (Z * stddev() / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        int gridLength = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        // StdOut.println("Grid length: " + gridLength);
        PercolationStats stats = new PercolationStats(gridLength, trials);
        // StdOut.println("trials = " + trials);
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi()+"]");
    }
}