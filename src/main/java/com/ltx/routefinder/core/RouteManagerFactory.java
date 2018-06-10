package com.ltx.routefinder.core;

/**
 * A simple factory class for {@link GraphRouteManager}
 */
public class RouteManagerFactory {

    public static RouteManager createGraphRouteManager() {
        return GraphRouteManager.newInstance();
    }
}
