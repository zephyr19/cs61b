package hw2;

import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private double[] sites;
    private double mean;
    private double stddev;

    /**
     * perform T independent experiments on an N-by-N grid.
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        sites = new double[T];
        double totalSites = 0;
        int divider = N * N;
        for (int i = 0; i < T; i++) {
            Percolation percolation = pf.make(N);
            int j;
            for (j = 1; !percolation.percolates(); j++) {
                while (percolation.numberOfOpenSites() != j) {
                    percolation.open(StdRandom.uniform(N), StdRandom.uniform(N));
                }
            }
            j--;
            sites[i] = (double) j / divider;
            totalSites += sites[i];
        }
        mean = (double) totalSites / T;
        double sum = 0;
        for (double site : sites) {
            sum += Math.pow(site - mean, 2);
        }
        stddev = Math.sqrt(sum / (T - 1));
    }

    /**
     * sample mean of percolation threshold.
     */
    public double mean() {
        return mean;
    }

    /**
     * sample standard deviation of percolation threshold.
     */
    public double stddev() {
        return stddev;
    }

    /**
     * low endpoint of 95% confidence interval.
     */
    public double confidenceLow() {
        return mean - 1.96 * stddev / Math.sqrt(sites.length);
    }

    /**
     * high endpoint of 95% confidence interval.
     */
    public double confidenceHigh() {
        return mean + 1.96 * stddev / Math.sqrt(sites.length);
    }
}
