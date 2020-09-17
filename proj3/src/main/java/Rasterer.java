import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    public Node[] xContents;
    public Node[] yContents;

    public class Node {
        public int index;
        public int fileLabel;
        public double Position;

        private Node(int indexing, int fileIndex, double Info) {
            index = indexing;
            fileLabel = fileIndex;
            Position = Info;
        }
    }

    public Rasterer() {
        xContents = new Rasterer.Node[(int) Math.pow(2,10)];
        yContents = new Rasterer.Node[(int) Math.pow(2,10)];
        for (int depth = 0; depth < 8; depth += 1) {
            double xgrid = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (Math.pow(2, (double)depth));
            double ygrid = (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT) / (Math.pow(2, (double)depth));
            double X = MapServer.ROOT_ULLON;
            double Y = MapServer.ROOT_ULLAT;
            int indexBase = (int) (Math.pow(2, depth) - 1);

            for (int index = 0; index <= indexBase; index += 1) {
                X =  X + xgrid;
                Y =  Y + ygrid;

                xContents[index + indexBase] = new Node(index + indexBase,index,X);
                yContents[index + indexBase] = new Node(index + indexBase,index,Y);
            }
        }
    }

    private int indexConvert(int fileIndex, int depth) {
        return fileIndex + (int) (Math.pow(2, depth) - 1);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
//        String p = Double.toString(xContents[10].Position);
//        String i = Integer.toString(xContents[10].index);
//        String f = Integer.toString(xContents[10].fileLabel);
//        System.out.println(p);
//        System.out.println(i);
//        System.out.println(f);
//        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
//                + "your browser.");

//        String[][] gridFiles = {{"d2_x0_y0.png", "d2_x1_y0.png"}, {"d2_x0_y1.png", "d2_x1_y1.png"}};
//        results.put("render_grid", gridFiles);
//        results.put("raster_ul_lon", -122.2998046875);
//        results.put("raster_ul_lat", 37.892195547244356);
//        results.put("raster_lr_lon", -122.255859375);
//        results.put("raster_lr_lat", 37.85749899038596);
//        results.put("depth", 2);
//        results.put("query_success", true);

        /** read input param and check if they are reasonable **/
        boolean query = true;
        if (params.get("ullon") > params.get("lrlon") || params.get("ullat") < params.get("lrlat")) {
            results.put("query_success", false);
            query = false;
        } else if (params.get("ullon") > MapServer.ROOT_LRLON || (params.get("lrlon") < MapServer.ROOT_ULLON) ||
                params.get("ullat") < MapServer.ROOT_LRLAT || params.get("lrlat") > MapServer.ROOT_ULLAT) {
            results.put("query_success", false);
            query = false;
        } else {
            results.put("query_success", true);
            query = true;
        }

        /** if the query box make no sense, no need to proceed **/
        if (!query) {
            results.put("raster_ul_lon", 0);
            results.put("raster_ul_lat", 0);
            results.put("raster_lr_lon", 0);
            results.put("raster_lr_lat", 0);
            results.put("depth", null);
            results.put("render_grid", null);
            return results;
        }

        /** adjust too large query box beyond the world **/
        double ulX = (params.get("ullon") > MapServer.ROOT_ULLON) ? params.get("ullon") : MapServer.ROOT_ULLON;
        double lrX = (params.get("lrlon") < MapServer.ROOT_LRLON) ? params.get("lrlon") : MapServer.ROOT_LRLON;
        double ulY = (params.get("ullat") < MapServer.ROOT_ULLAT) ? params.get("ullat") : MapServer.ROOT_ULLAT;
        double lrY = (params.get("lrlat") > MapServer.ROOT_LRLAT) ? params.get("lrlat") : MapServer.ROOT_LRLAT;
        double w = params.get("w");

        /** calculate the depth **/
        int d = getDepth(w, ulX, lrX);
        System.out.println(Integer.toString(d));
        results.put("depth", d);

        /** Get the topLeft and lowerRight filename and Lon and Lat position **/
        Node ulXNode = helperX(ulX, d);
        Node lrXNode = helperX(lrX, d);
        Node ulYNode = helperY(ulY, d);
        Node lrYNode = helperY(lrY, d);
        int ulFileX =  ulXNode.fileLabel;
        int lrFileX =  lrXNode.fileLabel;
        int ulFileY =  ulYNode.fileLabel;
        int lrFileY =  lrYNode.fileLabel;

        double xGrid = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (Math.pow(2, (double)d));
        double yGrid = (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT) / (Math.pow(2, (double)d));

        /** Add the results of the Lon and Lat **/
        results.put("raster_ul_lon", ulXNode.Position-xGrid);
        results.put("raster_ul_lat", ulYNode.Position-yGrid);
        results.put("raster_lr_lon", lrXNode.Position);
        results.put("raster_lr_lat", lrYNode.Position);
        /** calculate how many files needed**/
        int k = 0;
        int j = 0;
        for (int m = ulFileY; m <= lrFileY; m += 1) {
            k += 1;
        }
        for (int n = ulFileX; n <= lrFileX; n += 1) {
            j += 1;
        }

        /** Use loop to Get the gridFiles **/
        String[][] gridFiles = new String[k][j];
        int row = 0;
        for (int m = ulFileY; m <= lrFileY; m += 1) {
            int col = 0;
            for (int n = ulFileX; n <= lrFileX; n += 1) {
                gridFiles[row][col] = "d" + Integer.toString(d) + "_x" + Integer.toString(n) + "_y" + Integer.toString(m) + ".png";
                col += 1;
            }
            row += 1;
        }
//        System.out.println(gridFiles[0][0]);
        results.put("render_grid", gridFiles);
        return results;
    }

    /* DONE: Find the correct depth for the query*/
    private int getDepth (double w, double ulX, double lrX) {
        double queryLonDPP = (lrX - ulX) / w;
        //the least depth must 1//
        double dLonDPP = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE / 2;
        int d;
        for (d = 1; d < 7; d += 1) {
            if (dLonDPP <= queryLonDPP) {
                break;
            }
            dLonDPP = dLonDPP / 2;
        }
        return d;
    }
    private Node helperX (double ulX, int depth) {
        Queue<Node> queueX = new Queue<>();
        queueX.enqueue(xContents[0]);
        int thisD = 0;
        while (thisD != depth) {
            Node thisNode = queueX.dequeue();
            //compare to the left and right of the "thisNode"
            if (ulX < xContents[leftChild(thisNode)].Position) {
                queueX.enqueue(xContents[leftChild(thisNode)]);
            } else {
                queueX.enqueue(xContents[rightChild(thisNode)]);
            }
            thisD += 1;
        }
        return queueX.dequeue();
    }

    private Node helperY (double ulY, int depth) {
        Queue<Node> queueY = new Queue<>();
        queueY.enqueue(yContents[0]);
        int thatD = 0;
        while (thatD != depth) {
            Node thisNode = queueY.dequeue();
            //compare to the left and right of the "thisNode"
            if (ulY > yContents[leftChild(thisNode)].Position) {
                queueY.enqueue(yContents[leftChild(thisNode)]);
            } else {
                queueY.enqueue(yContents[rightChild(thisNode)]);
            }
            thatD += 1;
        }
        return queueY.dequeue();
    }
//
    private int leftChild(Node parent) {
        return parent.index * 2 + 1;
    }
    private int rightChild(Node parent) {
        return parent.index * 2 + 2;
    }
}
