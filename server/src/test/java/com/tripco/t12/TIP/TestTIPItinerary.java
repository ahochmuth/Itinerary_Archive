package com.tripco.t12.TIP;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestTIPItinerary {

    /* Radius and location values shared by test cases */
    private final int version2 = 2;

    private String optimization = "none";

    private Map<String, Object> invalid;

    private Map<String, Object> oobLatitude;
    private Map<String, Object> oobLongitude;

    private Map<String, Object> csu;
    private Map<String, Object> NearCSU;

    private Map<String, Object> nanOptions;
    private List<Map> nanPlaces;

    private List<Map> noPlaces;
    private Map<String, Object> noPlacesOptions;
    private List<Long> noPlacesDistances;

    private List<Map> onePlace;
    private Map<String, Object> onePlaceOptions;
    private List<Long> onePlaceDistances;

    @Before
    public void createLocationsForTestCases() {
        csu = new HashMap<>();
        csu.put("latitude", "40.576179");
        csu.put("longitude", "-105.080773");
        csu.put("name", "Oval, Colorado State University, Fort Collins, Colorado, USA");

        invalid = new HashMap<>();

        oobLatitude = new HashMap<>();
        oobLatitude.put("latitude", "89.000");
        oobLatitude.put("longitude", "0");
        oobLatitude.put("name", "Nowhere, USA");

        oobLongitude = new HashMap<>();
        oobLongitude.put("latitude", "0");
        oobLongitude.put("longitude", "-181");
        oobLongitude.put("name", "Nowhere, USA");

        NearCSU = new HashMap<>();
        NearCSU.put("latitude", "40");
        NearCSU.put("longitude", "-105");
        NearCSU.put("name", "NearCSU");

        /////////////////////////////////////////////////////////////////////////////////////
        nanOptions = new HashMap<>();
        nanOptions.put("title", "My fantastic and amazingly long itinerary test case trip");
        nanOptions.put("earthRadius", "3958");

        nanPlaces = new ArrayList<>();
        nanPlaces.add(csu);
        nanPlaces.add(NearCSU);

        /////////////////////////////////////////////////////////////////////////////////////
        //No Places Test
        noPlacesOptions = new HashMap<>();
        noPlacesOptions.put("title", "NoPlaces");
        noPlacesOptions.put("earthRadius", "3958.761316");

        noPlaces = new ArrayList<>();
        noPlacesDistances = new ArrayList<>();

        /////////////////////////////////////////////////////////////////////////////////////
        //One Place Test
        onePlaceOptions = new HashMap<>();
        onePlaceOptions.put("title", "OnePlaces");
        onePlaceOptions.put("earthRadius", "3958.761316");

        onePlace = new ArrayList<>();
        onePlace.add(csu);

    }

    @Test
    public void testOriginDestinationSame() throws ClientSideException {
        TIPItinerary trip = new TIPItinerary(version2, nanOptions, nanPlaces, optimization);
        trip.buildResponse();
        List<Long> expect = new ArrayList<>();
        expect.add(40L);
        expect.add(40L);
        List<Long> actual;
        actual = trip.getDistances();
        assertEquals("Distances are as expected for two locations", expect, actual);
    }

    @Test
    public void testNoPlaces() throws ClientSideException {
        //NoPlaces Test
        TIPItinerary trip = new TIPItinerary(version2, noPlacesOptions, noPlaces, optimization);
        trip.buildResponse();
        List<Long> expect2 = new ArrayList<>();
        List<Long> actual2;
        actual2 = trip.getDistances();
        assertEquals("NoPlaces should result in Empty ArrayList", expect2, actual2);
    }

    @Test
    public void testOnePlace() throws ClientSideException {
        //OnePlace Test
        TIPItinerary trip = new TIPItinerary(version2, onePlaceOptions, onePlace, optimization);
        trip.buildResponse();
        List<Long> expect3 = new ArrayList<>();
        List<Long> actual3;
        expect3.add(0L);
        actual3 = trip.getDistances();
        assertEquals("One place should result in no distance", expect3, actual3);
    }
    @Test
    public void testOptimizationNone() throws ClientSideException {
        TIPItinerary trip = new TIPItinerary(version2, onePlaceOptions, onePlace, optimization);
        trip.buildResponse();
        String expected = "none";
        String actual = trip.getOptimization();
        assertEquals("Optimization should be initialized as none", expected, actual);
    }

}
