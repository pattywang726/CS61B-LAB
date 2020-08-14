package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import static org.junit.Assert.*;

public class Percolation {
    private int[] sites;
    private int n;
    private WeightedQuickUnionUF WQU;
    private int openNum = 0;
    private boolean ifPercolate = false;
    private int bottomP;
    // to avoid backwash
    private WeightedQuickUnionUF ufExcludeBottom;

    // create N-by-N grid, with all sites initially blocked
    // this is constructor
    public Percolation(int N) {
        //The constructor should throw a java.lang.IllegalArgumentException if N ≤ 0.
        if (N<=0) {
            throw new IllegalArgumentException();
        }
        n = N;
        sites = new int[N * N + 2 + N];
        // add two virtual sites: top and bottom;
        bottomP = N * N + 1;
        WQU = new WeightedQuickUnionUF(N * N + 2);
        ufExcludeBottom = new WeightedQuickUnionUF(N * N + 1);

        //The constructor should take time proportional to N square; this runtime depends on
        // the time of building the WeightedQuickUnionUF, ~N * N elements.

        /* The connection of virtual tip or bottom is carried out when open sites.
           thus, the isOpen() doesn't have to be included in the isFull() */
//        for (int i = 0; i < N; i += 1) {
//            WQU.union(0, XYto1D(0, i));
//            WQU.union(bottomP, XYto1D(N-1, i));
//        }
//
//        for (int row = 0; row < N; row++) {
//            for (int col = 0; col < N; col++) {
//                int Num = XYto1D(row, col);
//                sites[Num] = 0;
//            }
//        }
    }

    private int XYto1D (int row, int col) {
        int num = row * n + col + 1;
        return num;
    }

    // validate the validity of (row, col)
    private void validate(int row, int col) {
        if (row < 0 || col < 0 || row >= n || col >= n) {
            throw new IndexOutOfBoundsException();
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int num = XYto1D(row, col);
        if (sites[num] == 0) {
            sites[num] = 1;
            openNum += 1;
            neighborOpen(row, col, num);
        }
    }

    // BE CAREFUL if this site is in the side.
    private void neighborOpen (int row, int col, int num) {
        // Connect tops to to the top virtual sites.
        if (row == 0) {
            WQU.union(num, 0);
            ufExcludeBottom.union(num, 0);
        }

        if (row == n - 1) {
            WQU.union(num, bottomP);
        }

        if (row >0 && isOpen(row-1, col)) {
            WQU.union(num, num-n);
            ufExcludeBottom.union(num, num-n);
        }
        if (row < n - 1 && isOpen(row+1, col)) {
            WQU.union(num, num+n);
            ufExcludeBottom.union(num, num+n);
        }
        if (col >0 && isOpen(row, col-1)) {
            WQU.union(num, num-1);
            ufExcludeBottom.union(num, num-1);
        }
        if (col < n - 1 && isOpen(row, col+1)) {
            WQU.union(num, num+1);
            ufExcludeBottom.union(num, num+1);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int num = XYto1D(row, col);
        return sites[num] != 0;
    }

//    private boolean isFullHelper(int row, int col) {
//        return isOpen(row, col) && WQU.connected(0, XYto1D(row, col));
//    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return ufExcludeBottom.connected(0, XYto1D(row, col));
    }

    /* work in the visualizer, HOWEVER, the "union" actions cannot be carried out
    in this checking function. If only open sites and doesn't call isFull(), this won't work.*/
//        if (row == (n - 1) && isFullHelper(row, col)) {
//            WQU.union(XYto1D(row, col), XYto1D(row, col)+n);
//        }
//        return isFullHelper(row, col);
//        }

    /* almost work, but still fail */
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
