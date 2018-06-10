package com.ltx.routefinder.core;

import java.util.List;

/**
 * Main interface, which had to be implemented to make application work
 */
public interface RouteManager {
    /**
     * Checks if two city are connected.
     *
     * @param city1 a name of source city
     * @param city2 a name of destination city
     * @return true is connected, false otherwise
     */
    boolean connected(String city1, String city2);

    /**
     * Returns a route from source city to destination one. Reversed input returns reversed route.
     * Returns a list of single city for the same city as source and destination.
     * Returns an empty list in case there is no route, or any (or both) city name is blank.
     *
     * @param city1 a name of source city
     * @param city2 a name of destination city
     * @return a route as a list of cities, starting from source (includes) to destination (included)
     */
    List<String> getRoute(String city1, String city2);

    /**
     * Connects source city with destination one.
     *
     * @param city1 a name of source city
     * @param city2 a name of destination city
     */
    void addConnection(String city1, String city2);
}