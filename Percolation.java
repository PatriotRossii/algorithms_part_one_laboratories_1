import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSites = 0;
    private int n, virtualTop, virtualBottom;
    private WeightedQuickUnionUF union;
    private boolean[] isSiteOpen;

    // create n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;

        int totalElements = n * n;
        virtualTop = totalElements;
        virtualBottom = totalElements + 1;

        union = new WeightedQuickUnionUF(totalElements + 2);
        // initialize virtual top and bottom
        for (int i = 1; i <= n; ++i) {
            union.union(getSiteAddress(1, i), virtualTop);
            union.union(getSiteAddress(n, i), virtualBottom);
        }

        isSiteOpen = new boolean[totalElements];
        for (int i = 0; i != totalElements; ++i) {
            isSiteOpen[i] = false;
        }
    }

    private int getSiteAddress(int row, int column) {
        return (row - 1) * n + (column - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if ((row < 1 || row > n) || (col < 1 || col > n))
            throw new IllegalArgumentException();

        if (!isOpen(row, col)) openSites += 1;
        isSiteOpen[getSiteAddress(row, col)] = true;

        if (row == 1) union.union(virtualTop, getSiteAddress(row, col));
        if (row == n) union.union(virtualBottom, getSiteAddress(row, col));

        if (row > 1 && isOpen(row - 1, col)) {
            union.union(getSiteAddress(row - 1, col), getSiteAddress(row, col));
        }
        if (row < n && isOpen(row + 1, col)) {
            union.union(getSiteAddress(row + 1, col), getSiteAddress(row, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            union.union(getSiteAddress(row, col - 1), getSiteAddress(row, col));
        }
        if (col < n && isOpen(row, col + 1)) {
            union.union(getSiteAddress(row, col + 1), getSiteAddress(row, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row < 1 || row > n) || (col < 1 || col > n))
            throw new IllegalArgumentException();

        return isSiteOpen[getSiteAddress(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row < 1 || row > n) || (col < 1 || col > n))
            throw new IllegalArgumentException();

        return isOpen(row, col) && (union.find(virtualTop) == union.find(getSiteAddress(row, col)));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return union.find(virtualBottom) == union.find(virtualTop);
    }

    // test client (optional)
    public static void main(String[] args) {
        // TODO
    }
}
