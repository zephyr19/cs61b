package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int openSites;
    private WeightedQuickUnionUF unionUF;
    private int sizeForUnion;

    /**
     * create N-by-N grid, with all sites initially blocked.
     */
    public Percolation(int N) {
        grid = new int[N][N];
        openSites = 0;
        sizeForUnion = N * N;
        unionUF = new WeightedQuickUnionUF(sizeForUnion + 1);
    }

    /**
     * open the site (row, col) if it is not open already.
     */
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row][col] = 1;
            int position = row * grid.length + col;
            int border = grid.length - 1;
            openSites++;
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
                }
            } else if (row != 0) {
                if (isOpenWithoutChecking(row - 1, col)) {
                    unionUF.union(position, position - grid.length);
                }
                if (row != border) {
                    if (isOpenWithoutChecking(row + 1, col)) {
                        unionUF.union(position, position + grid.length);
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
        if (row >= grid.length || col >= grid.length) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (row < 0 || col < 0) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    /**
     * is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        checkForRowCol(row, col);
        return unionUF.connected(row * grid.length + col, sizeForUnion);
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
        int i = grid.length - 1;
        for (int j = 0; j < grid.length; j++) {
            if (isFull(i, j)) {
                return true;
            }
        }
        return false;
    }

    /**
     * use for unit testing (not required, but keep this here for the autograder)
     */
    public static void main(String[] args) {

    }
}
