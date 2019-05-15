package com.tripco.t12.misc;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

//import sun.tools.tree.NewArrayExpression;

import static org.junit.Assert.assertEquals;


public class TestGreatCircleDistance {
    private final double csuLat = 40.576179;
    private final double csuLong = -105.080773;
    private final double delta = 0.01;

    private final float earthRadiusK = 6371;
    private final float earthRadiusMiles = 3959;

    //add more distancesfromcsu for further tests
    private Map<String, Object> RockBottom;
    private final double rbDistFromCsu = 92.0;

    private final double centerFromCsu = 11275.0;

    private Map<String, Object> HongKong;
    private final double hkDistFromCsu = 11925.0;

    private Map<String, Object> SydneyOpera;
    private final double soDistFromCsu = 13431.0;

    private Map<String, Object> Christ;
    private final double chDistFromCsu = 9522.0;

    private Map<String, Object> ColoradoSW;
    private Map<String, Object> ColoradoNE;
    private final double coDiaganoldis = 750.0;


    @Before
    public void createTestCases(){
        RockBottom = new HashMap<>();
        RockBottom.put("latitude", "39.7475");
        RockBottom.put("longitude", "-104.9947");
        RockBottom.put("name", "Rock Bottom Brewery, Denver, Colorado, USA");

        HongKong = new HashMap<>();
        HongKong.put("latitude", "22.3964");
        HongKong.put("longitude", "114.1095");
        HongKong.put("name", "Hong Kong");

        SydneyOpera = new HashMap<>();
        SydneyOpera.put("latitude", "-33.8568");
        SydneyOpera.put("longitude", "151.2153");
        SydneyOpera.put("name", "Sydney OperaHouse, Sydney, Australia");

        Christ = new HashMap<>();
        Christ.put("latitude", "-22.9519");
        Christ.put("longitude", "-43.2105");
        Christ.put("name", "Christ the Redeemer, Rio, Brazil");

        ColoradoNE = new HashMap<>();
        ColoradoNE.put("latitude", "41");
        ColoradoNE.put("longitude", "-109");
        ColoradoNE.put("name", "Northeast Colorado, Colorado");

        ColoradoSW = new HashMap<>();
        ColoradoSW.put("latitude", "37");
        ColoradoSW.put("longitude", "-102");
        ColoradoSW.put("name", "Southwest Colorado, Colorado");
    }

    @Test
    public void testHaversine(){
        double rblat = Double.parseDouble(RockBottom.get("latitude").toString());
        double rblong = Double.parseDouble(RockBottom.get("longitude").toString());
        double actual = GreatCircleDistance.haversine(csuLat, csuLong, rblat, rblong, earthRadiusK);
        double expect = rbDistFromCsu;
        assertEquals("actual and expected distances are the same", expect, actual, delta);

        double alat = csuLat;
        double along = csuLong;
        double aactual = GreatCircleDistance.haversine(csuLat, csuLong, csuLat, csuLong, earthRadiusK);
        double aexpect = 0;
        assertEquals("actual and expected distances", aexpect, aactual, delta);

        double blat = 0;
        double blong = 0;
        double bactual = GreatCircleDistance.haversine(csuLat, csuLong, blat, blong, earthRadiusK);
        double bexpect = centerFromCsu;
        assertEquals("actual and expected distances", bexpect, bactual, delta);

        double clat = Double.parseDouble(HongKong.get("latitude").toString());
        double clong = Double.parseDouble(HongKong.get("longitude").toString());
        double cactual = GreatCircleDistance.haversine(csuLat, csuLong, clat, clong, earthRadiusK);
        double cexpect = hkDistFromCsu;
        assertEquals("actual and expected distances", cexpect, cactual, delta);

        double dlat = Double.parseDouble(SydneyOpera.get("latitude").toString());
        double dlong = Double.parseDouble(SydneyOpera.get("longitude").toString());
        double dactual = GreatCircleDistance.haversine(csuLat, csuLong, dlat, dlong, earthRadiusK);
        double dexpect = soDistFromCsu;
        assertEquals("actual and expected distances", dexpect, dactual, delta);

        double elat = Double.parseDouble(Christ.get("latitude").toString());
        double elong = Double.parseDouble(Christ.get("longitude").toString());
        double eactual = GreatCircleDistance.haversine(csuLat, csuLong, elat, elong, earthRadiusK);
        double eexpect = chDistFromCsu;
        assertEquals("actual and expected distances", eexpect, eactual, delta);

        //double flat = Double.parseDouble(coDiaganoldis.get("latitude").toString());

    }

    @Test
    public void testZeroDist() {
        // Put in a variety of points that are the same
        // Are they zero distance apart?
        for(int latitude = -180; latitude <= 180; latitude += 45) {
            for(int longitude = -180; longitude <= 180; longitude += 45) {
                long dist = GreatCircleDistance.haversine(latitude, longitude, latitude, longitude, earthRadiusMiles);
                assertEquals(0, dist);
            }
        }
    }

    @Test
    public void testCities() {
        // Check distance function against online calculator
        // at https://www.freemaptools.com/how-far-is-it-between.htm
        // NY:  40.741895, -73.989308
        // LA:  34.0537, -118.243
        // CSU: 40.570936, -105.086553
        assertEquals( 2446, GreatCircleDistance.haversine(40.741895, -73.989308, 34.0537,   -118.243,    earthRadiusMiles));
        assertEquals( 1622, GreatCircleDistance.haversine(40.741895, -73.989308, 40.570936, -105.086553, earthRadiusMiles));
        assertEquals( 850,  GreatCircleDistance.haversine(34.0537,   -118.243,   40.570936, -105.086553, earthRadiusMiles));
        assertEquals( 121,  GreatCircleDistance.haversine(38.8339,   -104.8214,   40.570936, -105.086553, earthRadiusMiles));
    }
}
