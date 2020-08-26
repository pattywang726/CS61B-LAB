package hw4.puzzle;
import edu.princeton.cs.algs4.*;

import java.util.Comparator;

public class Solver {
    public int MOVES;
    public int totalM = 0;
    public WorldState start;
    public Stack<WorldState> solution = new Stack<>();

    public Solver(WorldState initial) {
        start = initial;
        MOVES = 0;

        class searchNode implements Comparator<searchNode>, Comparable<searchNode>{
            public int M;
            public searchNode prevNode;
            public WorldState world;

            public searchNode(WorldState initial) {
                world = initial;
                M = 0;
                prevNode = null;
            }

            @Override
            public int compare(searchNode o1, searchNode o2) {
                return (o1.world.estimatedDistanceToGoal() + o1.M) - (o2.world.estimatedDistanceToGoal() + o2.M);
            }

            @Override
            public int compareTo(searchNode o) {
                return (this.world.estimatedDistanceToGoal() + this.M) - (o.world.estimatedDistanceToGoal() + o.M);
            }
        }
        /* why this doesn't work, maybe since the searchNode didn't implement the "comparator" or "comparable"
        * f*ck! since I missed a parentheses: (o1.world.estimatedDistanceToGoal() + o1.M - o2.world.estimatedDistanceToGoal() + o2.M)*/
//        class sortRule implements Comparator<searchNode> {
//            @Override
//            public int compare(searchNode o1, searchNode o2) {
//                return (o1.world.estimatedDistanceToGoal() + o1.M) - (o2.world.estimatedDistanceToGoal() + o2.M);
//            }
//        }

        MinPQ<searchNode> PQ = new MinPQ<searchNode>(10);
        PQ.insert(new searchNode(start));

        searchNode X = PQ.delMin();
        while (X.world.estimatedDistanceToGoal() != 0) {
            for (WorldState neighbor : X.world.neighbors()) {
                /* neighbor is a WorldState, while X.prevNode is a Node: useless condition for optimization.*/
                if (X.prevNode != null && neighbor.equals(X.prevNode.world)) {
                    continue;
                }
                searchNode newNode = new searchNode(neighbor);
                newNode.M = X.M + 1;
                newNode.prevNode = X;
                PQ.insert(newNode);
                //number of total things ever enqueued in your MinPQ
                totalM += 1;
            }
            X = PQ.delMin();
        }
        while (X != null) {
            MOVES += 1;
            solution.push(X.world);
            X = X.prevNode;
        }
    }

    public int moves() {
        return MOVES-1;
    }

    public Iterable<WorldState> solution(){
        return solution;
    }
}
