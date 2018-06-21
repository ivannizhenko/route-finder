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
    public void emptyRouteManagerReturnsEmptyPath() {
        routeManager = new GraphRouteManager();

        assumeTrue(routeManager.isEmpty());
        assertTrue(routeManager.getRoute("Los Angeles", "San Francisco").isEmpty());
    }

    @Test
    public void nonEmptyRouteManagerIfSomeEdgesAdded() {
        routeManager = new GraphRouteManager();

        assumeTrue(routeManager.isEmpty());
        routeManager.addConnection("Los Angeles", "San Francisco");

        assertFalse(routeManager.isEmpty());
    }

    @Test
    public void routeManagerReturnsEmptyRouteIfSourceIsMissing() {
        assumeFalse(routeManager.isEmpty());
        assertTrue(routeManager.getRoute("Los Angeles", "New York").isEmpty());
    }

    @Test
    public void routeManagerReturnsConnectedFalseIfSourceIsMissing() {
        assumeFalse(routeManager.isEmpty());
        assertFalse(routeManager.connected("Los Angeles", "New York"));
    }

    @Test
    public void routeManagerReturnsEmptyRouteIfDestinationIsMissing() {
        assumeFalse(routeManager.isEmpty());
        assertTrue(routeManager.getRoute("New York", "Los Angeles").isEmpty());
    }

    @Test
    public void routeManagerReturnsConnectedFalseIfDestinationIsMissing() {
        assumeFalse(routeManager.isEmpty());
        assertFalse(routeManager.connected("New York", "Los Angeles"));
    }

    @Test
    public void routeManagerReturnsEmptyRouteIfSourceAndDestinationAreNotConnected() {
        assertTrue(routeManager.getRoute("New York", "Atlanta").isEmpty());
    }

    @Test
    public void routeManagerReturnsConnectedFalseIfSourceAndDestinationAreNotConnected() {
        assertFalse(routeManager.connected("New York", "Atlanta"));
    }

    @Test
    public void routeManagerReturnsEmptyRouteIfSourceIsNull() {
        assertTrue(routeManager.getRoute(null, "Atlanta").isEmpty());
    }

    @Test
    public void routeManagerReturnsConnectedFalseIfSourceIsNull() {
        assertFalse(routeManager.connected(null, "Atlanta"));
    }

    @Test
    public void routeManagerReturnsEmptyRouteIfDestinationIsNull() {
        assertTrue(routeManager.getRoute("Atlanta", null).isEmpty());
    }

    @Test
    public void routeManagerReturnsConnectedFalseIfDestinationIsNull() {
        assertFalse(routeManager.connected("Atlanta", null));
    }

    @Test
    public void routeManagerReturnsEmptyRouteIfSourceIsEmptyString() {
        assertTrue(routeManager.getRoute("", "Atlanta").isEmpty());
    }

    @Test
    public void routeManagerReturnsConnectedFalseIfSourceIsEmptyString() {
        assertFalse(routeManager.connected("", "Atlanta"));
    }

    @Test
    public void routeManagerReturnsEmptyRouteIfDestinationIsEmptyString() {
        assertTrue(routeManager.getRoute("Atlanta", "").isEmpty());
    }

    @Test
    public void routeManagerReturnsConnectedFalseIfDestinationIsEmptyString() {
        assertFalse(routeManager.connected("Atlanta", ""));
    }

    @Test
    public void routeManagerReturnsEmptyRouteIfSourceIsBlankString() {
        assertTrue(routeManager.getRoute(" ", "Atlanta").isEmpty());
    }

    @Test
    public void routeManagerReturnsConnectedFalseIfSourceIsBlankString() {
        assertFalse(routeManager.connected(" ", "Atlanta"));
    }

    @Test
    public void routeManagerReturnsEmptyRouteIfDestinationIsBlankString() {
        assertTrue(routeManager.getRoute("Atlanta", " ").isEmpty());
    }

    @Test
    public void routeManagerReturnsConnectedFalseIfDestinationIsBlankString() {
        assertFalse(routeManager.connected("Atlanta", " "));
    }

    @Test
    public void routeManagerReturnsShortestRouteIfSeveralRoutesArePresent() {
        List<String> route = routeManager.getRoute("New York", "Washington");

        assertFalse(route.isEmpty());
        assertEquals(4, route.size());
        assertEquals("[New York, Newark, Philadelphia, Washington]", route.toString());
    }

    @Test
    public void routeManagerReturnsConnectedTrueIfRouteIsPresent() {
        assertTrue(routeManager.connected("New York", "Washington"));
    }

    @Test
    public void routeManagerReturnsRevertedRouteIfCitiesAreReversed() {
        List<String> route = routeManager.getRoute("Washington", "New York");

        assertFalse(route.isEmpty());
        assertEquals(4, route.size());
        assertEquals("[Washington, Philadelphia, Newark, New York]", route.toString());
    }

    @Test
    public void routeManagerReturnsConnectedTrueIfRouteIsReversed() {
        assertTrue(routeManager.connected("Washington", "New York"));
    }

    @Test
    public void routeManagerReturnsOneElementRouteIfSourceAndDestinationAreTheSame() {
        List<String> route = routeManager.getRoute("New York", "New York");

        assertFalse(route.isEmpty());
        assertEquals(1, route.size());
        assertEquals("New York", route.get(0));
    }

    @Test
    public void routeManagerReturnsConnectedTrueIfSourceAndDestinationAreTheSame() {
        assertTrue(routeManager.connected("New York", "New York"));
    }
}