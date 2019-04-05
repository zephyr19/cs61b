public class BubbleGrid {
    private int[][] grid;

    /** Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
    }

    /** Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        int row = grid.length;
        int col = grid[0].length;
        int ufLen = row * col;
        UnionFind uf = new UnionFind(ufLen + 1);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (darts[i][j] == 1) {
                    uf.union(ufLen, xyTo1D(i, j));
                }
            }
        }
        int[] res = new int[darts.length];
        for (int k = 0; k < res.length; k++) {
            int i = darts[k][0];
            int j = darts[k][1];
            boolean isPop = false;
            if (grid[i][j] == 1) {
                grid[i][j] = 0;
                uf.minus(ufLen, xyTo1D(i, j));
                res[k]++;
                isPop = true;
            }
            while (isPop) {
                isPop = false;
            }
        }
        return res;
    }

    private int xyTo1D(int row, int col) {
        return row * grid[0].length + col;
    }
}
