package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] grid;
    private int openSites;
    private WeightedQuickUnionUF unionUF;
    private WeightedQuickUnionUF antiBackWash;
    private int virtualTop;
    private int virtualBottom;
    private int N;

    /**
     * create N-by-N grid, with all sites initially blocked.
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.N = N;
        openSites = 0;
        virtualTop = N * N;
        grid = new boolean[virtualTop + 1];
        virtualBottom = virtualTop + 1;
        unionUF = new WeightedQuickUnionUF(virtualTop + 1 + 1);
        antiBackWash = new WeightedQuickUnionUF(virtualTop + 1);
        init();
    }

    /**
     * Initialize uf.
     */
    private void init() {
        for (int i = 0; i < N; i++) {
            antiBackWash.union(i, virtualTop);
        }
        for (int i = xyTo1D(N - 1, 0); i < virtualTop; i++) {
            unionUF.union(i, virtualBottom);
        }
    }

    /**
     * Generates and fills array of nearby sites: top, bottom, left and right
     * array is used because of the spec restrictions
     */
    private int[] neighbour(int row, int col) {
        int[] neighbour = {-1, -1, -1, -1}; // neighbour[0] top; [1] right; [2] bottom; [3] left.
        if (row == 0) {
            neighbour[0] = xyTo1D(row, col); // if only one row, add itself.
        }
        if (row > 0 && row != N - 1) {
            neighbour[0] = xyTo1D(row - 1, col);
            neighbour[2] = xyTo1D(row + 1, col);
        } else if (row == 0) {
            neighbour[2] = xyTo1D(row + 1, col);
        } else {
            neighbour[0] = xyTo1D(row - 1, col);
        }
        if (col > 0 && col != N - 1) {
            neighbour[3] = xyTo1D(row, col - 1);
            neighbour[1] = xyTo1D(row, col + 1);
        } else if (col == 0) {
            neighbour[1] = xyTo1D(row, col + 1);
        } else {
            neighbour[3] = xyTo1D(row, col - 1);
        }
        return neighbour;
    }

    /**
     * open the site (row, col) if it is not open already.
     */
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[xyTo1D(row, col)] = true;
            int position = xyTo1D(row, col);
            openSites++;
            for (int neighbour : neighbour(row, col)) {
                if (neighbour != -1 && grid[neighbour]) {
                    antiBackWash.union(position, neighbour);
                    unionUF.union(position, neighbour);
                    if (antiBackWash.connected(position, virtualTop)) {
                        unionUF.union(position, virtualTop);
                    }
                }
            }
        }
    }

    /**
     * is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        checkForRowCol(row, col);
        return grid[xyTo1D(row, col)];
    }

    private void checkForRowCol(int row, int col) {
        if (row >= N || col >= N || row < 0 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    /**
     * is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        checkForRowCol(row, col);
        int position = xyTo1D(row, col);
        return isOpen(row, col) && antiBackWash.connected(position, virtualTop);
    }

    /**
     * returns the one dimensional position with row and col.
     */
    private int xyTo1D(int row, int col) {
        return row * N + col;
    }

    /**
     * number of open sites
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * does the system percolate?
     */
    public boolean percolates() {
        return unionUF.connected(virtualTop, virtualBottom);
    }

    /**
     * use for unit testing (not required, but keep this here for the autograder)
     */
    public static void main(String[] args) {

    }
}
