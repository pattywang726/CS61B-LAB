package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import java.lang.Math;

public class PercolationStats {
    private int T;
    private int N;
    private PercolationFactory pf;
    private double[] countOpen;
    /* public Integer[] openArray; */

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        this.T= T;
        this.N = N;
        this.pf = pf;
//        openArray = new Integer[N * N];
        for (int i = 0; i < T; i += 1) {
            Percolation test = pf.make(N);
            while (!test.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                test.open(row, col);
            }
            double OpenPercen = test.numberOfOpenSites() / (double) (N * N);
            countOpen[i] = OpenPercen;
//            openArray[i] = Integer.valueOf(OpenPercen);
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
        double lowP = mean() - (1.96 * stddev()) / Math.sqrt(Double.valueOf(T));
        return lowP;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double highP = mean() + (1.96 * stddev()) / Math.sqrt(Double.valueOf(T));
        return highP;
    }


}
