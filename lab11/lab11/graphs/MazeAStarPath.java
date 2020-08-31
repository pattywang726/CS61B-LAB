package lab11.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private MinPQ<Integer> PQ;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        PQ = new MinPQ<Integer>(maze.V(), new sort());
        PQ.insert(s);
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    class sort implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return h((int) o1) - h((int) o2);
        }
    }


    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        s = PQ.delMin();
        marked[s] = true;
        announce();
        while (s != t) {
            for (int neighbor : maze.adj(s)) {
                if (!marked[neighbor] && neighbor != edgeTo[s]) {
                    PQ.insert(neighbor);
                }
            }
            int v = PQ.delMin();
            marked[v] = true;
            edgeTo[v] = s;
            distTo[v] = distTo[s] + 1;
            announce();
            s = v;
        }
        // Done

    }

    @Override
    public void solve() {
        astar(s);
    }

}

