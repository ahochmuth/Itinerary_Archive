package com.tripco.t12.TIP;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/** Verifies the operation of the TIP distance class and its buildResponse method.
 */
public class TestTIPDistance {

  /* Radius and location values shared by test cases */
  private final float radiusMiles = 3958;
  private final int version = 1;
  private final int version2 = 2;
  private final double v2radiusMiles = 3958.761316;

  private Map<String, Object> csu;
  private Map<String, Object> invalid;
  private Map<String, Object> oobLatitude;
  private Map<String, Object> oobLongitude;
  private Map<String, Object> nanLongitude;

  @Before
  public void createLocationsForTestCases() {
    csu = new HashMap<>();
    csu.put("latitude", "40.576179");
    csu.put("longitude", "-105.080773");
    csu.put("name", "Oval, Colorado State University, Fort Collins, Colorado, USA");

    invalid = new HashMap<>();

    oobLatitude = new HashMap<>();
    oobLatitude.put("latitude", "90.0001");
    oobLatitude.put("longitude", "0");
    oobLatitude.put("name", "Nowhere, USA");

    oobLongitude = new HashMap<>();
    oobLongitude.put("latitude", "0");
    oobLongitude.put("longitude", "-181");
    oobLongitude.put("name", "Nowhere, USA");

    nanLongitude = new HashMap<>();
    nanLongitude.put("latitude", "40.576179");
    nanLongitude.put("longitude", "cat");
    nanLongitude.put("name", "cat");
  }

  @Test
  public void testOriginDestinationSame() {
    TIPDistance trip = new TIPDistance(version, csu, csu, radiusMiles);
    trip.buildResponse();
    long expect = 0;
    long actual = trip.getDistance();
    assertEquals("origin and destination are the same", expect, actual);
  }

  @Test(expected = NullPointerException.class)
  public void testInvalidOrigin() {
    TIPDistance trip = new TIPDistance(version, invalid, csu, radiusMiles);
    trip.buildResponse();
  }

  @Test(expected = NullPointerException.class)
  public void testInvalidDestination() {
    TIPDistance trip = new TIPDistance(version, csu, invalid, radiusMiles);
    trip.buildResponse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidLatitude() {
    TIPDistance trip = new TIPDistance(version, csu, oobLatitude, radiusMiles);
    trip.buildResponse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidLongitude() {
    TIPDistance trip = new TIPDistance(version, csu, oobLongitude, radiusMiles);
    trip.buildResponse();
  }

  @Test(expected = NumberFormatException.class)
  public void testNotANumber() {
    TIPDistance trip = new TIPDistance(version, csu, nanLongitude, radiusMiles);
    trip.buildResponse();
  }

  @Test
  public void v2testOriginDestinationSame() {
    TIPDistance trip = new TIPDistance(version2, csu, csu, v2radiusMiles);
    trip.buildResponse();
    long expect = 0;
    long actual = trip.getDistance();
    assertEquals("origin and destination are the same", expect, actual);
  }

  @Test(expected = NullPointerException.class)
  public void v2testInvalidOrigin() {
    TIPDistance trip = new TIPDistance(version2, invalid, csu, v2radiusMiles);
    trip.buildResponse();
  }

  @Test(expected = NullPointerException.class)
  public void v2testInvalidDestination() {
    TIPDistance trip = new TIPDistance(version2, csu, invalid, v2radiusMiles);
    trip.buildResponse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void v2testInvalidLatitude() {
    TIPDistance trip = new TIPDistance(version2, csu, oobLatitude, v2radiusMiles);
    trip.buildResponse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void v2testInvalidLongitude() {
    TIPDistance trip = new TIPDistance(version2, csu, oobLongitude, v2radiusMiles);
    trip.buildResponse();
  }

  @Test(expected = NumberFormatException.class)
  public void v2testNotANumber() {
    TIPDistance trip = new TIPDistance(version2, csu, nanLongitude, v2radiusMiles);
    trip.buildResponse();
  }

}
