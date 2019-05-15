package com.tripco.t12.TIP;

import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Verifies the operation of the TIP config class and its buildResponse method.
 */
public class TestTIPConfig {
  private TIPConfig conf;
  private TIPConfig confTwo;

  @Before
  public void createConfigurationForTestCases(){
    conf = new TIPConfig();
    conf.buildResponse();
    confTwo = new TIPConfig();
    confTwo.requestVersion = 4;
    confTwo.buildResponse();
  }

  @Test
  public void testType() {
    String type = conf.getType();
    assertEquals("config requestType", "config", type);
  }

  @Test
  public void testVersion() {
    int version = 5;
    assertEquals("config requestVersion", version, conf.getVersion());
  }



  @Test
  public void testServerName() {
    String name = conf.getServerName();
    assertEquals("config name", "t12 Team Spirit", name);
  }

  @Test
  public void testPlaceAttributes() {
    List<String> attr = conf.getPlaceAttributes();
    assertEquals("config attribute size", 9, attr.size());
  }

  @Test
  public void testPlaceAttributesVersionTwo() {
    List<String> attr = confTwo.getPlaceAttributes();
    assertEquals("config attribute size", 9, attr.size());
  }

  @Test
  public void testOptimizations() {
    List<String> opts = conf.getOptimizations();
    List<String> expected = Arrays.asList("none", "short", "shorter");
    assertEquals("config optimizations", expected, opts);
  }

  @Test
  public void testJSInitialization() {
    String body = "{}";
    Gson jsonConverter = new Gson();
    TIPConfig tipRequest = jsonConverter.fromJson(body, TIPConfig.class);
    tipRequest.buildResponse();
    assertEquals("javascript TIPConfig construction", 5, tipRequest.getVersion());
  }

  @Test
  public void expectOptimizations() {
    int actual = conf.getOptimizations().size();
    int expected = 3;
    assertEquals(actual, expected);
  }

  @Test
  public void testFilters() {
    List<Map> actualFilters = conf.getFilters().subList(0, 1);
    Map typeFilter = new HashMap();
    typeFilter.put("name", "type");
    typeFilter.put("values", Arrays.asList("airport", "heliport", "balloonport", "closed"));
    List<Map> expectedFilters = Arrays.asList(typeFilter);
    assertEquals("config optimizations", expectedFilters, actualFilters);
  }
}
