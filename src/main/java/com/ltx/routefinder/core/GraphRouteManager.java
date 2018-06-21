package com.ltx.routefinder.core;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Implementation of {@link RouteManager}. Based on Guava's Graph implementation.
 * Uses BFS to find a route between cities.
 * Please use {@link RouteManagerFactory} to create an instance of this class.
 */
class GraphRouteManager implements RouteManager {

    private static final List<String> EMPTY_LIST = Collections.emptyList();

    // Guava's graph implementation
    private MutableGraph<String> cityGraph;

    // can be replaced with cache (e.g. Guava's LoadingCache) if required
    private Table<String, String, List<String>> routeTable;

    // make constructor package-private to use for tests only
    GraphRouteManager() {
        cityGraph = GraphBuilder.undirected().build();
        routeTable = HashBasedTable.create();
    }

    static RouteManager newInstance() {
        return new GraphRouteManager();
    }

    /**
     * Adds a connection from source city to destination one.
     * Does nothing if any (or both) city name is blank.
     * Correctness of input values are responsibility of the client.
     *
     * @param city1 a name of source city
     * @param city2 a name of destination city
     */
    @Override
    public void addConnection(String city1, String city2) {
        if (isBlank(city1) || isBlank(city2))
            return;

        cityGraph.putEdge(city1, city2);
    }

    /**
     * Checks if two cities are connected.
     * Returns false, if any (or both) city name is blank.
     *
     * @param city1 a name of source city
     * @param city2 a name of destination city
     * @return true is connected, false otherwise, or any (or both) city name is incorrect
     */
    @Override
    public boolean connected(String city1, String city2) {
        if (isBlank(city1) || isBlank(city2))
            return false;

        List<String> route = routeTable.get(city1, city2);
        if (route == null)
            route = getRoute(city1, city2);

        return !route.isEmpty();
    }

    /**
     * Returns a route from source city to destination one. Reversed input returns reversed route.
     * Returns a list of single city for the same city as source and destination.
     * Returns an empty list in case there is no route, or any (or both) city name is blank.
     *
     * @param city1 a name of source city
     * @param city2 a name of destination city
     * @return a route as a list of cities, starting from source (includes) to destination (included),
     * or empty list if cities are not connected, or any (or both) city name is incorrect
     */
    @Override
    public List<String> getRoute(String city1, String city2) {
        if (isBlank(city1) || isBlank(city2))
            return EMPTY_LIST;

        List<String> route;

        if (routeTable.contains(city1, city2)) {
            route = routeTable.get(city1, city2);
        } else {
            route = calculateRoute(city1, city2);
            routeTable.put(city1, city2, route);
        }
        return route;
    }

    private List<String> calculateRoute(String city1, String city2) {
        Graph<String> graph = getImmutableCityGraph();

        Map<String, Boolean> visited = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        Deque<String> queue = new ArrayDeque<>();

        // input verification
        Set<String> nodes = graph.nodes();
        if (nodes.size() == 0 || !nodes.contains(city1) || !nodes.contains(city2))
            return EMPTY_LIST;

        if (city1.equals(city2))
            return Collections.singletonList(city1);

        // get start node, mark as visited, add to queue
        visited.put(city1, true);
        queue.add(city1);

        while (!queue.isEmpty()) {

            // dequeue next element
            String next = queue.poll();

            // get all unvisited neighbors, mark them as visited, put them in queue
            for (String node : graph.adjacentNodes(next)) {
                if (visited.getOrDefault(node, false))
                    continue;
                visited.put(node, true);
                parent.put(node, next);
                queue.add(node);

                // if destination node is found
                if (node.equals(city2)) {
                    return getRouteAsList(city1, node, parent);
                }
            }
        }
        return EMPTY_LIST;
    }

    private ImmutableGraph<String> getImmutableCityGraph() {
        return ImmutableGraph.copyOf(cityGraph);
    }

    private List<String> getRouteAsList(String source, String destination, Map<String, String> parent) {

        LinkedList<String> route = new LinkedList<>();

        String current = destination;
        route.addFirst(current);

        // add parents, including destination
        while (!current.equals(source)) {
            current = parent.get(current);
            route.addFirst(current);
        }
        return route;
    }

    boolean isEmpty() {
        return cityGraph.nodes().isEmpty();
    }
}