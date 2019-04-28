import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class BubbleGrid {
    private int[][] grid;
    private int length;

    /** Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        length = grid[0].length * grid.length + 1;
    }

    /** Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        WeightedQuickUnionUF removeUF = new WeightedQuickUnionUF(length + 1);
        WeightedQuickUnionUF checkUF = new WeightedQuickUnionUF(length + 1);

        for (int i = 1; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] != 0) {
                    removeUF.union(xyTo1D(i, j), length);
                    checkUF.union(xyTo1D(i, j), length);
                }
            }
        }
        int[] res = new int[darts.length];
        for (int n = 0; n < darts.length; n++) {
            int i = darts[n][0];
            int j = darts[n][1];
            int ijTo1D = xyTo1D(i, j);
            if (grid[i][j] != 0) {
                removeUF.
            } else {
                res[n] = 0;
            }
        }
        return null;
    }

    private int xyTo1D(int row, int col) {
        return row * grid[0].length + col;
    }
}
