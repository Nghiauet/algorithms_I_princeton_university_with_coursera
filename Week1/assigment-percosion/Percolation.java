import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private boolean[] size;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufForIsFull;
    private int n;
    private int virPoint1;
    private int virPoint2;
    private int openSizeNumber;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid must have at least one row and column");
        }
        this.size = new boolean[n * n + 2];
        this.uf = new WeightedQuickUnionUF(n * n + 2); 
        this.ufForIsFull = new WeightedQuickUnionUF(n * n + 2);
        this.n = n;
        this.virPoint1 = 0;
        this.virPoint2 = n * n + 1;
        size[virPoint1] = true;
        size[virPoint2] = false;
        
        this.openSizeNumber = 0;

        for (int j = 1; j < n + 1; j++) {
            this.uf.union(this.virPoint1, this.idOf(1, j));
            this.ufForIsFull.union(this.virPoint1, this.idOf(1, j));
            this.uf.union(this.virPoint2, this.idOf(n, j));
        }
    }

    private int idOf(int i, int j) {
        return (this.n * (i - 1)) + (j);
    }

    private void checkIfOutOfBound(int i, int j) {
        if (i < 1 || i > this.n) {
            throw new IllegalArgumentException("i is out of bound");
        }
        if (j < 1 || j > this.n) {
            throw new IllegalArgumentException("j is out of bound");
        }
    }

    public void open(int i, int j) {
        checkIfOutOfBound(i, j);
        if (this.isOpen(i, j)) {
            return;
        }
        openSizeNumber++;

        this.size[idOf(i, j)] = true;
        if (i > 1 && this.isOpen(i - 1, j)) {
            this.uf.union(this.idOf(i, j), this.idOf(i - 1, j));
            this.ufForIsFull.union(this.idOf(i, j), this.idOf(i - 1, j));
        }
        if (j > 1 && this.isOpen(i, j - 1)) {
            this.uf.union(this.idOf(i, j), this.idOf(i, j - 1));
            this.ufForIsFull.union(this.idOf(i, j), this.idOf(i, j - 1));
        }
        if (j < this.n && this.isOpen(i, j + 1)) {
            this.uf.union(this.idOf(i, j), this.idOf(i, j + 1));
            this.ufForIsFull.union(this.idOf(i, j), this.idOf(i, j + 1));
        }
        if (i < this.n && this.isOpen(i + 1, j)) {
            this.uf.union(this.idOf(i, j), this.idOf(i + 1, j));
            this.ufForIsFull.union(this.idOf(i, j), this.idOf(i + 1, j));
        }
    }

    public boolean isOpen(int i, int j) {
        checkIfOutOfBound(i, j);
        return this.size[idOf(i, j)];
    }

    public boolean isFull(int i, int j) {

        return (isOpen(i, j) && this.ufForIsFull.find(this.idOf(i, j)) == this.ufForIsFull.find(this.virPoint1));
    }

    public int numberOfOpenSites() {
        return openSizeNumber;
    }

    public boolean percolates() {
        if (this.n == 1) {
            return isOpen(1, 1);
        }
        if (this.n == 2) {
            return (isOpen(1, 1) && isOpen(2, 1)) || (isOpen(1, 2) && isOpen(2, 2));
        }
        return (this.uf.find(this.virPoint1) == this.uf.find(this.virPoint2));
    }

    public static void main(String[] args) {
        int n = 2;
        Percolation percolation = new Percolation(n);
        for (int i = 0; i < n * n + 2; i++) {
        StdOut.print(percolation.uf.find(i) + " ");
        }
        StdOut.println();
        for (int i = 0; i < n + 2; i++) {
        StdOut.print(percolation.size[i] + " ");
        }
        // StdOut.println();
        // test client
        percolation.open(1, 1);
        // percolation.open(2, 1);
        // percolation.open(1, n);
        StdOut.println("percolation.isOpen(1, n) = " + percolation.isOpen(1, n));
        StdOut.println("percolation.isFull(1, n) = " + percolation.isFull(1, n));
        StdOut.println("percolation.percolates() = " + percolation.percolates());
        // StdOut.println("percolation.uf.find(n) = " + percolation.uf.find(1));
        // for (int i = 0; i < n * n + 2; i++) {
        // StdOut.print(percolation.uf.find(i) + " ");
        // }
    }
}
