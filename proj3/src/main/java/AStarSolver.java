import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class AStarSolver {
    // parent of the vertex //
    private Map<Long, Long> parentV;
    // distance from source
    private Map<Long, Double> distS;
    private Map<Long, Double> distT;
    // marked: prevent multiple copies
//    private Map<Long, Boolean> marked;
    private List<Long> route = new ArrayList<>();
    private MinPQ<vertex> PQ;

    public AStarSolver(GraphDB g, Long s, Long t) {
        parentV = new HashMap<>();
        distS = new HashMap<>();
        distT = new HashMap<>();
//        marked = new HashMap<>();

        for (Long vertice: g.vertices()) {
            distS.put(vertice, Double.POSITIVE_INFINITY);
            distT.put(vertice, g.distance(vertice, t));
//            marked.put(vertice, false);
        }

        parentV.put(s, s);
        distS.put(s, 0.0);
        PQ = new MinPQ<>();
        PQ.insert(new vertex(s, distS.get(s) + distT.get(s)));

        vertex n = PQ.delMin();
//        marked.put(n.v, true);
        // n.v != t will fail; since == means the same memory box; but equal means they have same value;
        while (!n.v.equals(t) && !PQ.isEmpty()) {
            for (Long w : g.adjacent(n.v)) {
                double neighborFromS = distS.get(n.v) + g.distance(n.v, w);
                if (neighborFromS < distS.get(w)) {
                    parentV.put(w, n.v);
                    distS.put(w, neighborFromS);
                    PQ.insert(new vertex(w, distS.get(w) + distT.get(w)));
                }
            }
            n =  PQ.delMin();
//            if (marked.get(n.v)) {
//                n = PQ.delMin();
//            }
//            marked.put(n.v, true);
        }

        long index = t;
        while (index != s) {
            route.add(index);
            index = parentV.get(index);
        }
        route.add(s);
    }

    class vertex implements Comparator<vertex>, Comparable<vertex> {
        Long v;
        Double dist;
        public vertex (Long v, Double dist) {
            this.v = v;
            this.dist = dist;
        }

        @Override
        public int compare(vertex v1, vertex v2) {
            return Double.compare(v1.dist, v2.dist);
        }

        @Override
        public int compareTo(vertex v) {
            return Double.compare(this.dist, v.dist);
        }
    }
    // BUG!!: Broken comparators that don’t handle cases like where two items are equal.!!
    public List<Long> solution() {
        List<Long> solution= new LinkedList<>();
        for (int i = route.size()-1; i >=0; i -= 1) {
            solution.add(route.get(i));
        }
        return solution;
    }

//    class sort implements Comparator<Long> {
//        Map<Long, Double> distT;
//        Map<Long, Double> distS;
//
//        public sort(Map<Long, Double> distT, Map<Long, Double> distS) {
//            this.distT = distT;
//            this.distS = distS;
//        }
//
//        @Override
//        public int compare(Long v1, Long v2) {
//            double d1 = distS.get(v1);
//            double d2 = distS.get(v2);
//            double h1 = distT.get(v1);
//            double h2 = distT.get(v2);
//            if (Double.compare((d1 + h1), (d2 + h2)) > 0) {
//                return 1;
//            } else {
//                return -1;
//            }
//        }
//    }
}
