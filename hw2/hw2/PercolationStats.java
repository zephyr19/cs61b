package hw2;

import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private int[] sites;
    private double mean;
    private double stddev;

    /** perform T independent experiments on an N-by-N grid. */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        sites = new int[T];
        int totalSites = 0;
        for (int i = 0; i < T; i++) {
            Percolation percolation = pf.make(N);
            for (int j = 1; !percolation.percolates(); j++) {
                while (percolation.numberOfOpenSites() != j) {
                    percolation.open(StdRandom.uniform(N), StdRandom.uniform(N));
                }
                totalSites += j;
                sites[i] = j;
            }
        }
        mean = (double) totalSites / T;
        int sum = 0;
        for (int site : sites) {
            sum += Math.pow(site - mean, 2);
        }
        stddev = Math.sqrt((double) sum / (sites.length - 1));
    }

    /** sample mean of percolation threshold. */
    public double mean() {
        return mean;
    }

    /** sample standard deviation of percolation threshold. */
    public double stddev() {
        return stddev;
    }

    /** low endpoint of 95% confidence interval. */
    public double confidenceLow() {
        return mean - 1.96 * stddev / Math.sqrt(sites.length);
    }

    /** high endpoint of 95% confidence interval. */
    public double confidenceHigh() {
        return mean + 1.96 * stddev / Math.sqrt(sites.length);
    }
}
