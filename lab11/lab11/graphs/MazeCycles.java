package lab11.graphs;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.nio.charset.IllegalCharsetNameException;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private WeightedQuickUnionUF WQU;
    private Maze maze;
    private Stack<Integer> stack;
    private boolean cycle = false;
    private int s;
    private int [] dashEdgeTo;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        s = maze.xyTo1D(1,1);
        dashEdgeTo = new int[maze.V()];
        WQU = new WeightedQuickUnionUF(m.V());
        stack = new Stack<Integer>();
        stack.push(s);
    }

    @Override
    public void solve() {
        checkCycle();
        // DONE: Your code here!
    }

    // Helper methods go here
    private void checkCycle() {
        while (!cycle) {
            s = stack.pop();
            for (int next : maze.adj(s)) {
                if (!marked[next]) {
                    stack.push(next);
                    marked[next] = true;
                    announce();
                    dashEdgeTo[next] = s;
                    WQU.union(next, s);
                } else if (next != dashEdgeTo[s] && WQU.connected(next, s)) {
                    cycle = true;
                    int last = dashEdgeTo[next];
                    edgeTo[last] = next;
                    edgeTo[next] = s;
                    int backTrace = s;
                    while (backTrace != last) {
                        edgeTo[backTrace] = dashEdgeTo[backTrace];
                        announce();
                        backTrace = dashEdgeTo[backTrace];
                    }
                    announce();
                    break;
                }
            }
        }
    }
}

