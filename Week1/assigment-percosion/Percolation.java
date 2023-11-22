import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private boolean[] size;
    private WeightedQuickUnionUF uf;
    private int n;
    private int virPoint1;
    private int virPoint2;
    // private boolean connectVirtualPoint;
    private int openSizeNumber;
    public Percolation(int n) {
        if (n <= 1) {
            throw new IllegalArgumentException("Grid must have at least one row and column");
        }
        this.size = new boolean[n*n+2];
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.n = n;
        this.virPoint1 = 0;
        this.virPoint2 = n * n + 1;

        this.openSizeNumber = 0;
        // StdOut.println("n = " + n);
        // for (int i = 1; i < n+1; i++) {
        //     for (int j = 1; j < n+1; j++) {
        //         StdOut.println("i = " + i + ", j = " + j);
        //         StdOut.println("idOf(i, j) = " + this.idOf(i, j));
        //     }
        // }
        for (int j = 1; j < n; j++) {
            // StdOut.println("print 2 idOf(0, j) = " + this.idOf(1, j)); 
            this.uf.union(this.virPoint1, this.idOf(1, j));
            this.uf.union(this.virPoint2, this.idOf(n , j));
        }
        // StdOut.println("virPoint1 = " + virPoint1);
    }

    private int idOf(int i, int j) {
        return (this.n * (i -1)) + (j);
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
        openSizeNumber++;
        
        this.size[idOf(i, j)] = true;
        if (i > 1 && this.isOpen(i - 1, j)) {
            this.uf.union(this.idOf(i, j), this.idOf(i - 1, j));
        }
        if (j > 1 && this.isOpen(i, j - 1)) {
            this.uf.union(this.idOf(i, j), this.idOf(i, j - 1));
        }
        if (j < this.n  && this.isOpen(i, j + 1)) {
            this.uf.union(this.idOf(i, j), this.idOf(i, j + 1));
        }
        if (i < this.n  && this.isOpen(i + 1, j)) {
            this.uf.union(this.idOf(i, j), this.idOf(i + 1, j));
        }
    }

    public boolean isOpen(int i, int j) {
        checkIfOutOfBound(i, j);
        return this.size[idOf(i, j)];
    }

    public boolean isFull(int i, int j) {

        return (isOpen(i , j) &&this.uf.find(this.idOf(i, j)) == this.uf.find(this.virPoint1));
    }

    public int numberOfOpenSites() {
        return openSizeNumber;
    }

    public boolean percolates() {
        return this.uf.find(this.virPoint1) == this.uf.find(this.virPoint2);
    }
    public static void main(String[] args) {

        Percolation percolation = new Percolation(6);
        //test client
        percolation.open(1, 6);
        percolation.open(2, 6);
        percolation.isOpen(1, 2);
        percolation.open(-1 , 1);
        percolation.open(3, 6);
        StdOut.println("percolation.isFull(1, 6) = " + percolation.isFull(1, 6));
        StdOut.println("percolation.isFull(2, 6) = " + percolation.isFull(2, 6));
        
    }
}
