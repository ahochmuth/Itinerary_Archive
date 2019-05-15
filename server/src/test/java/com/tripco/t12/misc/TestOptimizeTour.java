package com.tripco.t12.misc;

import com.google.gson.Gson;
import com.tripco.t12.TIP.ClientSideException;
import com.tripco.t12.TIP.TIPItinerary;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class TestOptimizeTour {
    @Test
    public void testPairwiseDistances() {
        String in = "{\n" +
                "  \"requestType\"    : \"itinerary\",\n" +
                "  \"requestVersion\" : 4,\n" +
                "  \"options\"        : { \"title\":\"My Trip\", \n" +
                "                       \"earthRadius\":\"3958.8\",\n" +
                "                       \"optimization\":\"short\" },\n" +
                "  \"places\"         : [{\"name\":\"Denver\",       \"latitude\": \"39.7\", \"longitude\": \"-105.0\"},\n" +
                "                      {\"name\":\"Boulder\",      \"latitude\": \"40.0\", \"longitude\": \"-105.4\"},\n" +
                "                      {\"name\":\"Fort Collins\", \"latitude\": \"40.6\", \"longitude\": \"-105.1\"}],\n" +
                "  \"distances\"      : []\n" +
                "}";
        TIPItinerary itinerary = new Gson().fromJson(in, TIPItinerary.class);
        long[][] expected = {
                {0, 30, 62},
                {30, 0, 44},
                {62, 44, 0},
        };
        long[][] actual = OptimizeTour.pairwiseDistances(itinerary.getPlacesAsLocations(), itinerary.getEarthRadius());
        assertArrayEquals(expected, actual);
    }
    @Test
    public void testNNEndToEnd() throws ClientSideException {
        String in = "{\n" +
                "  \"requestType\"    : \"itinerary\",\n" +
                "  \"requestVersion\" : 4,\n" +
                "  \"options\"        : { \"title\":\"My Trip\", \n" +
                "                       \"earthRadius\":\"3958.8\",\n" +
                "                       \"optimization\":\"short\" },\n" +
                "  \"places\"         : [{\"name\":\"Denver\",       \"latitude\": \"39.7\", \"longitude\": \"-105.0\"},\n" +
                "                      {\"name\":\"Boulder\",      \"latitude\": \"40.0\", \"longitude\": \"-105.4\"},\n" +
                "                      {\"name\":\"Fort Collins\", \"latitude\": \"40.6\", \"longitude\": \"-105.1\"}],\n" +
                "  \"distances\"      : []\n" +
                "}";
        TIPItinerary itinerary = new Gson().fromJson(in, TIPItinerary.class);
        Map firstLocation = itinerary.getPlaces().get(0);
        itinerary.buildResponse();
        // optimizing tour must not change first place on tour
        Map firstLocationAfterOptimize = itinerary.getPlaces().get(0);
        assertEquals(firstLocation, firstLocationAfterOptimize);
    }
    @Test
    public void testNoOptimizationOption() throws ClientSideException {
        String in = "{\n" +
                "  \"requestType\"    : \"itinerary\",\n" +
                "  \"requestVersion\" : 4,\n" +
                "  \"options\"        : { \"title\":\"My Trip\", \n" +
                "                       \"earthRadius\":\"3958.8\"\n" +
                "                        },\n" +
                "  \"places\"         : [{\"name\":\"Denver\",       \"latitude\": \"39.7\", \"longitude\": \"-105.0\"},\n" +
                "                      {\"name\":\"Boulder\",      \"latitude\": \"40.0\", \"longitude\": \"-105.4\"},\n" +
                "                      {\"name\":\"Fort Collins\", \"latitude\": \"40.6\", \"longitude\": \"-105.1\"}],\n" +
                "  \"distances\"      : []\n" +
                "}";
        TIPItinerary itinerary = new Gson().fromJson(in, TIPItinerary.class);
        List<Map> placesInitial = itinerary.getPlaces();
        itinerary.buildResponse();
        // No optimization set? Then places shouldn't change
        List<Map> placesAfter = itinerary.getPlaces();
        assertEquals(placesInitial, placesAfter);
    }
    @Test(expected = ClientSideException.class)
    public void testInvalidOptimizationOption() throws ClientSideException {
        String in = "{\n" +
                "  \"requestType\"    : \"itinerary\",\n" +
                "  \"requestVersion\" : 4,\n" +
                "  \"options\"        : { \"title\":\"My Trip\", \n" +
                "                       \"earthRadius\":\"3958.8\",\n" +
                "                       \"optimization\":\"ljaldsjljflk\" },\n" +
                "  \"places\"         : [{\"name\":\"Denver\",       \"latitude\": \"39.7\", \"longitude\": \"-105.0\"},\n" +
                "                      {\"name\":\"Boulder\",      \"latitude\": \"40.0\", \"longitude\": \"-105.4\"},\n" +
                "                      {\"name\":\"Fort Collins\", \"latitude\": \"40.6\", \"longitude\": \"-105.1\"}],\n" +
                "  \"distances\"      : []\n" +
                "}";
        TIPItinerary itinerary = new Gson().fromJson(in, TIPItinerary.class);
        List<Map> placesInitial = itinerary.getPlaces();
        // Invalid optimization should throw exception, and cause 400 error
        itinerary.buildResponse();
    }
    @Test
    public void testNNReorder() throws ClientSideException {
        String in = "{\n" +
                "  \"requestType\"    : \"itinerary\",\n" +
                "  \"requestVersion\" : 4,\n" +
                "  \"options\"        : { \"title\":\"My Trip\", \n" +
                "                       \"earthRadius\":\"3958.8\",\n" +
                "                       \"optimization\":\"short\" },\n" +
                "  \"places\"         : [{\"name\":\"Boulder\",      \"latitude\": \"40.0\", \"longitude\": \"-105.4\"},\n" +
                "                      {\"name\":\"Fort Collins\", \"latitude\": \"40.6\", \"longitude\": \"-105.1\"},\n" +
                "                      {\"name\":\"Denver\",       \"latitude\": \"39.7\", \"longitude\": \"-105.0\"}],\n" +
                "  \"distances\"      : [24, 41, 59]\n" +
                "}";
        TIPItinerary itinerary = new Gson().fromJson(in, TIPItinerary.class);
        itinerary.buildResponse();
        String loc1 = (String) itinerary.getPlaces().get(0).get("name");
        String loc2 = (String) itinerary.getPlaces().get(1).get("name");
        String loc3 = (String) itinerary.getPlaces().get(2).get("name");

        // Denver is closer to Boulder than Fort Collins
        // It should start at Boulder, go to Denver, then go
        // to Fort Collins.
        assertEquals("Boulder", loc1);
        assertEquals("Denver", loc2);
        assertEquals("Fort Collins", loc3);
    }

    @Test
    public void testNNInternal() {
        long[][] distances = {
                {0, 2, 1},
                {1, 0, 1},
                {1, 2, 0}
        };
        Tour actual = OptimizeTour.nearestNeighbor(distances, 0);
        int[] expected = new int[] {0, 2, 1};
        assertArrayEquals(expected, actual.tour);
    }

    @Test
    public void testNNInternalEquidistant() {
        long[][] distances = {
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}
        };
        Tour actual = OptimizeTour.nearestNeighbor(distances, 0);
        // expect list to be in order
        int[] expected = new int[] {0, 1, 2};
        assertArrayEquals(expected, actual.tour);
    }

    @Test
    public void testNNInternalNonZeroStart() {
        long[][] distances = {
            {0, 1, 1},
            {1, 0, 1},
            {1, 1, 0}
        };
        Tour actual = OptimizeTour.nearestNeighbor(distances, 2);
        // expect list to be in order
        int[] expected = new int[] {2, 0, 1};
        assertArrayEquals(expected, actual.tour);
    }

    @Test
    public void testNNInternalZeroPlaces() {
        long[][] distances = {};
        Tour actual = OptimizeTour.nearestNeighbor(distances, 0);
        // expect list to be in order
        int[] expected = {};
        assertArrayEquals(expected, actual.tour);
    }

    @Test
    public void testNNInternalOnePlace() {
        long[][] distances = {
            {0}
        };
        Tour actual = OptimizeTour.nearestNeighbor(distances, 0);
        // expect list to be in order
        int[] expected = {0};
        assertArrayEquals(expected, actual.tour);
    }

    @Test
    public void testRotate() {
        Tour input = new Tour(new int[] {0, 1, 2, 3});
        int[] expected = {1, 2, 3, 0};
        input.rotateTour(1);
        assertArrayEquals(expected, input.tour);
    }

    @Test
    public void testRotateOneLen() {
        Tour input = new Tour(new int[] {0});
        int[] expected = {0};
        input.rotateTour(100);
        assertArrayEquals(expected, input.tour);
    }

    @Test
    public void testRotateZeroLen() {
        Tour input = new Tour(new int[] {});
        int[] expected = {};
        input.rotateTour(0);
        assertArrayEquals(expected, input.tour);
    }

    @Test
    public void testTourDistanceOneLen() {
        long[][] distances = {
            {0}
        };
        Tour tour = new Tour(new int[] {0});
        int expected = 0;
        long actual = tour.totalDistance(distances);
        assertEquals(expected, actual);
    }

    @Test
    public void testTourDistanceZeroLen() {
        long[][] distances = {};
        Tour tour = new Tour(new int[] {});
        int expected = 0;
        long actual = tour.totalDistance(distances);
        assertEquals(expected, actual);
    }

    @Test
    public void testDis() {
        int[] t = {2, 1, 0};
        Tour tour = new Tour(t);
        long[][] distances = {
                {0, 40, 20},
                {15, 0, 15},
                {20, 40, 0}
        };
        int j = 0;
        int k = 2;
        long expected = 20;
        long actual = OptimizeTour.dis(tour.tour, distances, j, k);
        assertEquals(expected, actual);
    }

    @Test
    public void testTwoOptReverse() {
        int[] tour = {5, 20, 60, 31, 42, 10};
        int j = 2;
        int k = 5;
        int[] actual = tour;
        OptimizeTour.twoOptReverse(actual, j, k);
        int[] expected = {5, 20, 10, 42, 31, 60};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testTwoOptImprove() {
        int[] t = {2, 0, 4, 1, 3};
        Tour actual = new Tour(t);
        long[][] distances = {
                {0, 40, 20, 5, 7},
                {40, 0, 15, 36, 90},
                {20, 15, 0, 28, 75},
                {5, 36, 28, 0, 20},
                {7, 90, 75, 20, 0}
        };
        int[] expected = {2, 1, 3, 4, 0};
        OptimizeTour.twoOpt(distances, actual);
        assertArrayEquals(expected, actual.tour);
    }

    @Test
    public void testTwoOpt() throws ClientSideException {
        String in = "{\n" +
                "  \"requestType\"    : \"itinerary\",\n" +
                "  \"requestVersion\" : 4,\n" +
                "  \"options\"        : { \"title\":\"My Trip\", \n" +
                "                       \"earthRadius\":\"3958.8\",\n" +
                "                       \"optimization\":\"shorter\"\n" +
                "                        },\n" +
                "  \"places\"         : [{\"id\":\"dnvr\",\"name\":\"Denver\",\"latitude\":\"39.7392\",\"longitude\":\"-104.9903\"},\n" +
                "                        {\"id\":\"uiuy\",\"name\":\"uiuyiyuiyu\",\"latitude\":\"40.01499\",\"longitude\":\"-104.98\"},\n" +
                "                        {\"id\":\"bldr\",\"name\":\"Boulder\",\"latitude\":\"40.01499\",\"longitude\":\"-105.27055\"},\n" +
                "                        {\"id\":\"plac\",\"name\":\"place\",\"latitude\":\"40.3\",\"longitude\":\"-105.27055\"},\n" +
                "                        {\"id\":\"sdaf\",\"name\":\"sdafasfsfs\",\"latitude\":\"40.3\",\"longitude\":\"-104.98\"},\n" +
                "                        {\"id\":\"foco\",\"name\":\"Fort Collins\",\"latitude\":\"40.585258\",\"longitude\":\"-105.084419\"}],\n" +
                "  \"distances\"      : [19, 15, 20, 15, 20, 59]\n" +
                "}";

        TIPItinerary itinerary = new Gson().fromJson(in, TIPItinerary.class);
        long expected = 125;
        itinerary.buildResponse();
        List<Long> a = itinerary.getDistances();
        long actual = 0;
        for (long value: a) {
            actual += value;
        }
        assertEquals(expected, actual);
    }
}
