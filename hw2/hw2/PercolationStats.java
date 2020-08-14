package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import java.lang.Math;

public class PercolationStats {
    private int numTrails;
    private double[] countOpen;

    // perform T independent experiments on an N-by-N grid
    // this is constructor
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        numTrails= T;
        // List building needs the instantiation; otherwise will throw the NullPointerException;
        countOpen = new double[T];
        for (int i = 0; i < T; i += 1) {
            Percolation test = pf.make(N);
            while (!test.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                test.open(row, col);
            }
            double OpenPercen = (double) test.numberOfOpenSites() / (N * N);
            countOpen[i] = OpenPercen;

        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(countOpen);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(countOpen);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double lowP = mean() - (1.96 * stddev()) / Math.sqrt(Double.valueOf(numTrails));
        return lowP;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double highP = mean() + (1.96 * stddev()) / Math.sqrt(Double.valueOf(numTrails));
        return highP;
    }

}
