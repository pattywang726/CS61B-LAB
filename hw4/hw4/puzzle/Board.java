package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState{
    private final int size;
    private final int[][] goal;
    private final int[][] world;

    public Board(int[][] tiles) {
        size = tiles.length;

        // make a copy of tiles and setting the goal tiles;
        goal = new int[size][size];
        world = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                goal[i][j] = i * 3 + (j + 1);
                world[i][j] = tiles[i][j];
            }
        }
        goal[size-1][size-1] = 0;
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i > size - 1 || j < 0 || j > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        return world[i][j];
    }

    public int size() {
        return size;
    }
    public Iterable<WorldState> neighbors() {
        // neignbor means the only one step move. only tile surrounding the 0 can move.
        // so neignbors are at most four;

        Queue<WorldState> neighbs = new Queue<>();
        int x = -1;
        int y = -1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tileAt(i, j) == 0) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }

        int [][] copy = new int[size][size];
        // don't use i and j;
        for (int copyi = 0; copyi < size; copyi++) {
            for (int copyj = 0; copyj < size; copyj++) {
                copy[copyi][copyj] = tileAt(copyi, copyj);
            }
        }

        for (int m = 0; m < size; m++) {
            for (int n = 0; n < size; n++) {
                if (Math.abs(-x + m) + Math.abs(n - y) - 1 == 0) {
                    copy[x][y] = copy[m][n];
                    copy[m][n] = 0;
                    Board neighbor = new Board(copy);
                    neighbs.enqueue(neighbor);
                    // back to original
                    copy[m][n] = copy[x][y];
                    copy[x][y] = 0;
                }
            }
        }
        return neighbs;
    }

    public int hamming() {
        int[] worLdList = buildWorldList();
        int numberWrong = 0;
        for (int a = 1; a < size*size + 1; a++) {
            if (a != worLdList[a] && worLdList[a] != 0) {
                numberWrong += 1;
            }
        }
        return numberWrong;
    }

    private int[] buildWorldList() {
        int[] worldList = new int[size * size + 1];
        int count = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                worldList[count] = tileAt(i, j);
                count += 1;
            }
        }
        return worldList;
    }

    private int positionX (int p) {
        int positionX;
        return (p % size == 0 ? size : p%size);
    }
    private int positionY (int p) {
        int positionY;
        return (p % size == 0 ? p/size - 1 : p/size);
    }

    public int manhattan() {
        int[] worLdList = buildWorldList();
        // index of the worldList represent the goal board;

        int mDistance = 0;
        for (int a = 1; a < size*size + 1; a++) {
            int stepDistance = 0;
            if (a != worLdList[a] && worLdList[a] != 0) {
                int xMove = Math.abs(positionX(worLdList[a]) - positionX(a));
                int yMove = Math.abs(positionY(worLdList[a]) - positionY(a));
                stepDistance += xMove + yMove;
            } else {
                stepDistance = 0;
            }
            mDistance += stepDistance;
        }
        return mDistance;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    private boolean equalsTest (int[][] thisworld, int[][] world1) {
        //*  checks that individual entries of array are equal
        //  *  argument is object of type Object
        //  *  argument is null
        //  *  argument is Board of different size

        if (this.size != world1.length) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (thisworld[i][j] != world1[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }

        Board board1 = (Board) y;

        if (world != null ? ! equalsTest(this.world, board1.world) : board1.world != null) {
            return false;
        }
        return goal != null ? equalsTest(this.goal, board1.goal) : board1.goal == null;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result = result * 10 + tileAt(i, j);
            }
        }
        return result;
    }
}
