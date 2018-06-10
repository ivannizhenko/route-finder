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
    public void testRouteManagerReturnsEmptyRouteIfSourceIsMissing() {
        assumeFalse(routeManager.isEmpty());
        assertTrue(routeManager.getRoute("Los Angeles", "New York").isEmpty());
    }

    @Test
    public void testRouteManagerReturnsConnectedFalseIfSourceIsMissing() {
        assumeFalse(routeManager.isEmpty());
        assertFalse(routeManager.connected("Los Angeles", "New York"));
    }

    @Test
    public void testRouteManagerReturnsEmptyRouteIfDestinationIsMissing() {
        assumeFalse(routeManager.isEmpty());
        assertTrue(routeManager.getRoute("New York", "Los Angeles").isEmpty());
    }

    @Test
    public void testRouteManagerReturnsConnectedFalseIfDestinationIsMissing() {
        assumeFalse(routeManager.isEmpty());
        assertFalse(routeManager.connected("New York", "Los Angeles"));
    }

    @Test
    public void testRouteManagerReturnsEmptyRouteIfSourceAndDestinationAreNotConnected() {
        List<String> route = routeManager.getRoute("New York", "Atlanta");

        assertTrue(route.isEmpty());
    }

    @Test
    public void testRouteManagerReturnsConnectedFalseIfSourceAndDestinationAreNotConnected() {
        assertFalse(routeManager.connected("New York", "Atlanta"));
    }

    @Test
    public void testRouteManagerReturnsEmptyRouteIfSourceIsEmptyString() {
        List<String> route = routeManager.getRoute("", "Atlanta");

        assertTrue(route.isEmpty());
    }

    @Test
    public void testRouteManagerReturnsConnectedFalseIfSourceIsEmptyString() {
        assertFalse(routeManager.connected("", "Atlanta"));
    }

    @Test
    public void testRouteManagerReturnsEmptyRouteIfDestinationIsEmptyString() {
        List<String> route = routeManager.getRoute("Atlanta", "");

        assertTrue(route.isEmpty());
    }

    @Test
    public void testRouteManagerReturnsConnectedFalseIfDestinationIsEmptyString() {
        assertFalse(routeManager.connected("Atlanta", ""));
    }

    @Test
    public void testRouteManagerReturnsShortestRouteIfSeveralRoutesArePresent() {
        List<String> route = routeManager.getRoute("New York", "Washington");

        assertFalse(route.isEmpty());
        assertEquals(4, route.size());
        assertEquals("[New York, Newark, Philadelphia, Washington]", route.toString());
    }

    @Test
    public void testRouteManagerReturnsConnectedTrueIfRouteIsPresent() {
        assertTrue(routeManager.connected("New York", "Washington"));
    }

    @Test
    public void testRouteManagerReturnsRevertedRouteIfCitiesAreReversed() {
        List<String> route = routeManager.getRoute("Washington", "New York");

        assertFalse(route.isEmpty());
        assertEquals(4, route.size());
        assertEquals("[Washington, Philadelphia, Newark, New York]", route.toString());
    }

    @Test
    public void testRouteManagerReturnsConnectedTrueIfRouteIsReversed() {
        assertTrue(routeManager.connected("Washington", "New York"));
    }

    @Test
    public void testRouteManagerReturnsOneElementRouteIfSourceAndDestinationAreTheSame() {
        List<String> route = routeManager.getRoute("New York", "New York");

        assertFalse(route.isEmpty());
        assertEquals(1, route.size());
        assertEquals("New York", route.get(0));
    }

    @Test
    public void testRouteManagerReturnsConnectedTrueIfSourceAndDestinationAreTheSame() {
        assertTrue(routeManager.connected("New York", "New York"));
    }
}