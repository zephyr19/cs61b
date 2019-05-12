package bearmaps.proj2c;

import bearmaps.hw4.WeightedEdge;
import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.MyTrieSet;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private WeirdPointSet pointSet;
    private Map<Point, Long> pointLongMap;
    private MyTrieSet trieSet;
    private Map<String, List<Node>> stringToNodes;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        List<Node> nodes = this.getNodes();
        pointLongMap = new HashMap<>();
        List<Point> points = new LinkedList<>();
        trieSet = new MyTrieSet();
        stringToNodes = new HashMap<>();
        for (Node node : nodes) {
            if (node.name() != null) {
                String cleanName = cleanString(node.name());
                trieSet.add(cleanName);
                if (stringToNodes.containsKey(cleanName)) {
                    stringToNodes.get(cleanName).add(node);
                } else {
                    stringToNodes.put(cleanName, new LinkedList<>());
                    stringToNodes.get(cleanName).add(node);
                }
            }
            if (neighbors(node.id()).size() > 0) {
                Point point = new Point(node.lon(), node.lat());
                points.add(point);
                pointLongMap.put(point, node.id());
            }
        }
        pointSet = new WeirdPointSet(points);
    }

    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point point = pointSet.nearest(lon, lat);
        return pointLongMap.get(point);
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> cleanName =  trieSet.keysWithPrefix(cleanString(prefix));
        List<String> res = new LinkedList<>();
        for (String name : cleanName) {
            List<Node> list = stringToNodes.get(name);
            for (Node node : list) {
                res.add(node.name());
            }
        }
        return res;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> res = new LinkedList<>();
        String cleanName = cleanString(locationName);
        List<Node> nodes = stringToNodes.get(cleanName);
        for (Node node : nodes) {
            Map<String, Object> map = new HashMap<>();
            map.put("lat", node.lat());
            map.put("lon", node.lon());
            map.put("name", node.name());
            map.put("id", node.id());
            res.add(map);
        }
        return res;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
