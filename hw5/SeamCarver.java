import edu.princeton.cs.algs4.Picture;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SeamCarver {
    private Picture p;

    public SeamCarver(Picture picture) {
        p = picture;
    }
    // current picture
    public Picture picture() {
        return p;
    }
    // width of current picture
    public int width() {
        return p.width();
    }
    // height of current picture
    public int height() {
        return p.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        // check if index out of range
        if (x < 0 || x > width()-1 || y < 0 || y > height()) {
            throw new java.lang.IndexOutOfBoundsException("Index out of range");
        }
        // initiate all pixels around the current one
        int left = x - 1;
        int right = x + 1;
        int top = y - 1;
        int bottom = y + 1;
        // check if it's border pixel for x-gradient
        if (x == 0) {
            left = width() - 1;
        } else if (x == width() - 1) {
            right = 0;
        }
        // check if it's border pixel for y-gradient
        if (y == 0) {
            top = height() - 1;
        } else if (y == height() - 1) {
            bottom = 0;
        }
        // x-gradient
        Color rightC = p.get(right, y);
        Color leftC = p.get(left, y);
        double Rx = Math.pow((rightC.getRed() - leftC.getRed()), 2);
        double Gx = Math.pow((rightC.getGreen() - leftC.getGreen()), 2);
        double Bx = Math.pow((rightC.getBlue() - leftC.getBlue()), 2);
        // y-gradient
        Color topC = p.get(x, top);
        Color bottomC = p.get(x, bottom);
        double Ry = Math.pow((topC.getRed() - bottomC.getRed()), 2);
        double Gy = Math.pow((topC.getGreen() - bottomC.getGreen()), 2);
        double By = Math.pow((topC.getBlue() - bottomC.getBlue()), 2);

        return Rx + Gx + Bx + Ry + Gy + By;
    }

    private int convert (int x, int y) {
        int index = x + y * width();
        return index;
    }

    // find the index which corresponds to the top left, top middle, and top right;
    private ArrayList<Integer> topList (int x, int y) {
        ArrayList<Integer> topList = new ArrayList<Integer>();
        // check if top row hit the border;
            topList.add(0, convert(x-1,y-1));
            topList.add(1, convert(x,y-1));
            topList.add(2, convert(x+1,y-1));
        if (x == 0) {
            topList.remove(0);
        } else if (x == width()-1) {
            topList.remove(2);
        }
        return topList;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[height()];
        ArrayList<Double> cost = new ArrayList<Double>();
        ArrayList<Integer> prev = new ArrayList<Integer>();

        for (int j = 0; j < height(); j += 1) {
            for (int i = 0; i < width(); i += 1) {
                if (j == 0) {
                    cost.add(convert(i, j), energy(i, j));
                    prev.add(convert(i, j),-1);
                } else {
                    // scan those potential three p in top row.
                    ArrayList<Integer> topIndex = topList(i, j);
                    Double minCost = cost.get(topIndex.get(0));
                    int minIndex = 0;
                    for (int k = 1; k < topIndex.size(); k += 1) {
                        if (cost.get(topIndex.get(k)) < minCost) {
                            minCost = cost.get(topIndex.get(k));
                            minIndex = k;
                        }
                    }
                    // add the current energy + cost from top row
                    cost.add(convert(i, j), energy(i, j) + minCost);
                    prev.add(convert(i, j), topIndex.get(minIndex));
                }
            }
        }

        // find the minimum value from the most bottom row.
        Double minTotal = cost.get(convert(0, height()-1));
        int colBottom = 0;
        for (int n = 1; n < width(); n += 1) {
            if (cost.get(convert(n,height()-1)) < minTotal ) {
                minTotal = cost.get(convert(n,height()-1));
                colBottom = n;
            }
        }
        // traverse back from bottom row to the top, m == how many rows
        seam[height()-1] = colBottom;
        for (int m = height()-2; m >= 0; m -= 1) {
            seam[m] = Math.floorMod(prev.get(convert(seam[m + 1], m + 1)), width());
        }
        return seam;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        // transpose the picture
        Picture transposed = new Picture(p.height(), p.width());
        Picture OriginalCopy = new Picture(p.width(), p.height());
        // loop over the original picture  and swap the col and row;
        for (int i = 0; i < p.width(); i += 1) {
            for (int j = 0; j < p.height(); j += 1) {
                transposed.set(j, i, p.get(i, j));
                OriginalCopy.set(i, j, p.get(i, j));
            }
        }
        p = transposed;
        // use FindVerticalSeam
        int[] horizontalSeam = findVerticalSeam();
        // transpose back
        p = OriginalCopy;
        return horizontalSeam;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        Picture newP = SeamRemover.removeHorizontalSeam(p, seam);
        p = newP;
        return;
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        Picture newP = SeamRemover.removeVerticalSeam(p, seam);
        p = newP;
        return;
    }
}
