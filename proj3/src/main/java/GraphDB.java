import example.CSCourseDB;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
     public Map<String, GraphDB.Node> Nodes = new LinkedHashMap<>();
     public Map<String, ArrayList<GraphDB.Edge>> Graph = new LinkedHashMap<>();

     static class Node {
         String id;
         String lon;
         String lat;
         String name;


         Node(String id, String lon, String lat, String name) {
             this.id = id;
             this.lon = lon;
             this.lat = lat;
             this.name = name;
         }
    }

     static class Edge {
         String nd2;
         String extraInfo;

         Edge(String nd2, String extraInfo) {
             this.nd2 = nd2;
             this.extraInfo = extraInfo;
        }
    }

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    void addNode(Node n) {
        if (!Graph.containsKey(n.id)) {
            this.Graph.put(n.id, null);
        }
        this.Nodes.put(n.id, n);
    }

    void addEdge(String n, Edge e) {
        if (Graph.containsKey(n) && Graph.get(n) != null) {
            Graph.get(n).add(e);
        } else {
            ArrayList<Edge> edgeList =new ArrayList<Edge>();
            edgeList.add(e);
            Graph.put(n, edgeList);
        }
    }

    void removeNode(String n) {
        this.Nodes.remove(n);
        this.Graph.remove(n);
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        Iterator<String> iter = Graph.keySet().iterator();
        while (iter.hasNext()) {
            String d = iter.next();
            if (Graph.get(d) == null) {
                iter.remove();
                Nodes.remove(d);
            }
        }
    }

    int V() {
        return Nodes.size();
    }


    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        ArrayList<Long> vList = new ArrayList<Long>();
        Set<String> keys = Nodes.keySet();
//        Set<String> keys = Graph.keySet();  Checked: Graph and Nodes have same length of keys;
        for (String d : keys) {
            vList.add(Long.parseLong(d));
        }
        //YOUR CODE HERE, this currently returns only an empty list.
        return vList;
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        ArrayList<Long> adjList = new ArrayList<Long>();
        String vNode = Long.toString(v);
        for (Edge e: Graph.get(vNode)) {
                adjList.add(Long.parseLong(e.nd2));
        }
        return adjList;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        Set<String> keys = Nodes.keySet();
        Node closestN = null;
        double closestD = 1000;
        for (String d : keys) {
            double thisD = distance(Double.parseDouble(Nodes.get(d).lon), Double.parseDouble(Nodes.get(d).lat), lon, lat);
            if (thisD < closestD) {
                closestD = thisD;
                closestN = Nodes.get(d);
            }
        }
        return Long.parseLong(closestN.id);
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        String V = Long.toString(v);
        String lon = Nodes.get(V).lon;
        return Double.parseDouble(lon);
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        String V = Long.toString(v);
        String lat = Nodes.get(V).lat;
        return Double.parseDouble(lat);
    }
}
