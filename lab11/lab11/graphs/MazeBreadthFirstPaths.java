package lab11.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private Queue<Integer> queue;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        // refer to the constructor: MazeSolver(Maze m) in the superClass: MazeSolver.
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        queue = new Queue<Integer>();
        queue.enqueue((Integer) s);
        // Add more variables here!
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int v) {
        marked[v] = true;
        announce();

        while (!queue.isEmpty()) {
            int w = (int) queue.dequeue();
            if (w == t) {
                targetFound = true;
                return;
            }
            for (int neighbors : maze.adj(w)) {
                if (!marked[neighbors]) {
                    queue.enqueue((Integer) neighbors);
                    distTo[neighbors] = distTo[w] + 1;
                    marked[neighbors] = true;
                    edgeTo[neighbors] = w;
                    announce();
                }
            }
        }
        // DONE: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
    }


    @Override
    public void solve() {
        bfs(s);
        // bfs();
    }
}

