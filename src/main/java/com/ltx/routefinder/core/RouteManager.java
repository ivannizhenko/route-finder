package com.ltx.routefinder.core;

import java.util.List;

/**
 * Main interface, which had to be implemented to make application work
 */
public interface RouteManager {

    boolean connected(String city1, String city2);

    List<String> getRoute(String city1, String city2);

    void addConnection(String city1, String city2);
}