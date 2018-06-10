package com.ltx.routefinder.core;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

public class GraphRouteManagerTest {

    private GraphRouteManager routeManager;

    @Before
    public void setUp() {
        routeManager = new GraphRouteManager();

        routeManager.addConnection("New York", "Newark");
        routeManager.addConnection("New York", "Hoboken");
        routeManager.addConnection("Hoboken", "Newark");
        routeManager.addConnection("Philadelphia", "Newark");
        routeManager.addConnection("Washington", "Philadelphia");
        routeManager.addConnection("Atlanta", "New Orleans");
        routeManager.addConnection("Atlanta", "");
    }

    @After
    public void tearDown() throws Exception {
        routeManager = null;
    }

    @Test
    public void testEmptyRouteManagerReturnsEmptyPath() {
        routeManager = new GraphRouteManager();

        assumeTrue(routeManager.isEmpty());
        assertTrue(routeManager.getRoute("Los Angeles", "San Francisco").isEmpty());
    }

    @Test
    public void testNonEmptyRouteManagerIfSomeEdgesAdded() {
        routeManager = new GraphRouteManager();

        assumeTrue(routeManager.isEmpty());
        routeManager.addConnection("Los Angeles", "San Francisco");

        assertFalse(routeManager.isEmpty());
    }

    @Test
    public void testRouteManagerReturnsEmptyPathIfSourceIsMissing() {
        assumeFalse(routeManager.isEmpty());
        assertTrue(routeManager.getRoute("Los Angeles", "New York").isEmpty());
    }

    @Test
    public void testRouteManagerReturnsConnectedFalseIfSourceIsMissing() {
        assumeFalse(routeManager.isEmpty());
        assertFalse(routeManager.connected("Los Angeles", "New York"));
    }

    @Test
    public void testRouteManagerReturnsEmptyPathIfDestinationIsMissing() {
        assumeFalse(routeManager.isEmpty());
        assertTrue(routeManager.getRoute("New York", "Los Angeles").isEmpty());
    }

    @Test
    public void testRouteManagerReturnsConnectedFalseIfDestinationIsMissing() {
        assumeFalse(routeManager.isEmpty());
        assertFalse(routeManager.connected("New York", "Los Angeles"));
    }

    @Test
    public void testRouteManagerReturnsEmptyPathIfSourceAndDestinationAreNotConnected() {
        List<String> route = routeManager.getRoute("New York", "Atlanta");

        assertTrue(route.isEmpty());
    }

    @Test
    public void testRouteManagerReturnsConnectedFalseIfSourceAndDestinationAreNotConnected() {
        assertFalse(routeManager.connected("New York", "Atlanta"));
    }

    @Test
    public void testRouteManagerReturnsShortestPathIfSeveralPathsArePresent() {
        List<String> route = routeManager.getRoute("New York", "Washington");

        assertFalse(route.isEmpty());
        assertEquals(4, route.size());
        assertEquals("[New York, Newark, Philadelphia, Washington]", route.toString());
    }

    @Test
    public void testRouteManagerReturnsConnectedTrueIfPathIsPresent() {
        assertTrue(routeManager.connected("New York", "Washington"));
    }

    @Test
    public void testRouteManagerReturnsRevertedPathIfCitiesAreReversed() {
        List<String> route = routeManager.getRoute("Washington", "New York");

        assertFalse(route.isEmpty());
        assertEquals(4, route.size());
        assertEquals("[Washington, Philadelphia, Newark, New York]", route.toString());
    }

    @Test
    public void testRouteManagerReturnsConnectedTrueIfPathIsReversed() {
        assertTrue(routeManager.connected("Washington", "New York"));
    }

    @Test
    public void testRouteManagerReturnsOneElementPathIfSourceAndDestinationAreTheSame() {
        List<String> route = routeManager.getRoute("New York", "New York");

        assertFalse(route.isEmpty());
        assertEquals(1, route.size());
        assertEquals("New York", route.get(0));
    }

    @Test
    public void testRouteManagerReturnsConnectedTrueIfSourceAndDestinationAreTheSame() {
        assertTrue(routeManager.connected("New York", "New York"));
    }

    @Test
    public void testRouteManagerAllowsEmptyStringForSource() {
        List<String> route = routeManager.getRoute("", "Atlanta");

        assertFalse(route.isEmpty());
        assertEquals(2, route.size());
        assertEquals("", route.get(0));
        assertEquals("Atlanta", route.get(1));
        assertTrue(routeManager.connected("", "Atlanta"));
    }

    @Test
    public void testRouteManagerAllowsEmptyStringForDestination() {
        List<String> route = routeManager.getRoute("Atlanta", "");

        assertFalse(route.isEmpty());
        assertEquals(2, route.size());
        assertEquals("Atlanta", route.get(0));
        assertEquals("", route.get(1));
        assertTrue(routeManager.connected("Atlanta", ""));
    }
}