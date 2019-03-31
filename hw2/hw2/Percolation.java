package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int openSites;
    private WeightedQuickUnionUF unionUF;
    private int sizeForUnion;
    private boolean isFirst = true;

    /**
     * create N-by-N grid, with all sites initially blocked.
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        grid = new int[N][N];
        openSites = 0;
        sizeForUnion = N * N;
        unionUF = new WeightedQuickUnionUF(sizeForUnion + 1 + 1);
    }

    /**
     * open the site (row, col) if it is not open already.
     */
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row][col] = 1;
            int position = xyTo1D(row, col);
            int border = grid.length - 1;
            openSites++;
            if (grid.length != 1) {
                if (row != 0 && col != 0 && col != border) {
                    if (isOpenWithoutChecking(row, col + 1)) {
                        unionUF.union(position, position + 1);
                    }
                    if (isOpenWithoutChecking(row, col - 1)) {
                        unionUF.union(position, position - 1);
                    }
                    if (isOpenWithoutChecking(row - 1, col)) {
                        unionUF.union(position, position - grid.length);
                    }
                    if (row != border) {
                        if (isOpenWithoutChecking(row + 1, col)) {
                            unionUF.union(position, position + grid.length);
                        }
                    } else {
                        if (isFirst) {
                            unionUF.union(position, sizeForUnion + 1);
                            isFirst = false;
                        }
                    }
                } else if (row != 0) {
                    if (isOpenWithoutChecking(row - 1, col)) {
                        unionUF.union(position, position - grid.length);
                    }
                    if (row != border) {
                        if (isOpenWithoutChecking(row + 1, col)) {
                            unionUF.union(position, position + grid.length);
                        }
                    } else {
                        if (isFirst) {
                            unionUF.union(position, sizeForUnion + 1);
                            isFirst = false;
                        }
                    }
                    if (col == 0) {
                        if (isOpenWithoutChecking(row, col + 1)) {
                            unionUF.union(position, position + 1);
                        }
                    } else {
                        if (isOpenWithoutChecking(row, col - 1)) {
                            unionUF.union(position, position - 1);
                        }
                    }
                } else {
                    unionUF.union(position, sizeForUnion);
                    if (isOpenWithoutChecking(row + 1, col)) {
                        unionUF.union(position, position + grid.length);
                    }
                }
            } else {
                unionUF.union(position, sizeForUnion);
                unionUF.union(position, sizeForUnion + 1);
            }
        }
    }

    /**
     * is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        checkForRowCol(row, col);
        return grid[row][col] == 1;
    }

    private boolean isOpenWithoutChecking(int row, int col) {
        return grid[row][col] == 1;
    }

    private void checkForRowCol(int row, int col) {
        if (row >= grid.length || col >= grid.length || row < 0 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    /**
     * is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        checkForRowCol(row, col);
        return unionUF.connected(xyTo1D(row, col), sizeForUnion);
    }

    /**
     * returns the one dimensional position with row and col.
     */
    private int xyTo1D(int row, int col) {
        return row * grid.length + col;
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
        return unionUF.connected(sizeForUnion, sizeForUnion + 1);
    }

    /**
     * use for unit testing (not required, but keep this here for the autograder)
     */
    public static void main(String[] args) {

    }
}
