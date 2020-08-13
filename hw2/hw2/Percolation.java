package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import static org.junit.Assert.*;
import org.junit.Test;

public class Percolation {
    public int[] sites;
    public int n;
    public WeightedQuickUnionUF WQU;
    public int openNum = 0;
    public boolean ifPercolate = false;
    public int bottomP;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        n = N;
        sites = new int[N * N + 2 + N];
        // add two virtual sites: top and bottom;
        bottomP = N * (N+1) + 1;
        WQU = new WeightedQuickUnionUF(N * N + 2 + N);
        for (int i = 0; i < N; i += 1) {
            WQU.union(0, XYto1D(0, i));
            WQU.union(bottomP, XYto1D(N, i));
        }

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                int Num = XYto1D(row, col);
                sites[Num] = 0;
            }
        }
    }

    private int XYto1D (int row, int col) {
        int num = row * n + col + 1;
        return num;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        int num = XYto1D(row, col);
        if (sites[num] == 0) {
            sites[num] = 1;
            openNum += 1;
            neighborOpen(row, col, num);
        } else {
            return;
        }
    }
    // BE CAREFUL if this site is in the side.
    private void neighborOpen (int row, int col, int num) {
        if (row >0 && isOpen(row-1, col)) {
            WQU.union(num, num-n);
        }
        if (row < n - 1 && isOpen(row+1, col)) {
            WQU.union(num, num+n);
        }
        if (col >0 && isOpen(row, col-1)) {
            WQU.union(num, num-1);
        }
        if (col < n - 1 && isOpen(row, col+1)) {
            WQU.union(num, num+1);
        }

        if (row == (n - 1) && WQU.connected(0, XYto1D(row, col))) {
            WQU.union(num, num+n);
        } else {
            return;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int num = XYto1D(row, col);
       return sites[num] != 0;
    }

    private boolean isFullHelper(int row, int col) {
        return isOpen(row, col) && WQU.connected(0, XYto1D(row, col));
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row == (n - 1) && isFullHelper(row, col)) {
            WQU.union(XYto1D(row, col), XYto1D(row, col)+n);
        }
        return isFullHelper(row, col);
        }
//        if (ifPercolate && isOpen(row, col) && row == (n - 1)) {
//            if (isFullHelper(row - 1, col)) {
//                return true;
//            } else if (col > 0 && isFullHelper(row, col - 1)) {
//                return true;
//            } else if (col < n - 1 && isFullHelper(row, col + 1)) {
//                return true;
//            } else {
//                return false;
//            }
//        }

    // number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return WQU.connected(0, bottomP);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        Percolation perc = new Percolation(5);
        perc.open(3,4);
        perc.open(2,4);
        perc.open(2,2);
        perc.open(2,3);
        perc.open(0,2);

        assertTrue(perc.isOpen(2,2));
        assertFalse(perc.isOpen(3,2));
        assertTrue(perc.isFull(0,2));

        perc.open(1,2);
        assertTrue(perc.isFull(1,2));
        assertTrue(perc.isFull(3,4));

        perc.open(4,4);
        assertTrue(perc.isFull(4,4));
        assertTrue(perc.percolates());
    }

}
