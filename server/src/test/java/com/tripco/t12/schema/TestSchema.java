package com.tripco.t12.schema;

import com.tripco.t12.TIP.TIPConfig;
import com.tripco.t12.TIP.TIPDistance;
import com.tripco.t12.TIP.TIPItinerary;
import com.tripco.t12.TIP.TIPFind;
import org.json.JSONObject;
import org.junit.Test;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//code in class modified from https://github.com/everit-org/json-schema
public class TestSchema {

    @Test
    public void validateConfigSchema() {
        TIPConfig tc = new TIPConfig();
        tc.buildResponse();
        Gson gson = new Gson();
        String configJSON = gson.toJson(tc);
        boolean actual = TIPSchemas.validateJsonAgainstSchema(tc.getClass(), configJSON);
        assertTrue("configSchema allows a valid JSONObject", actual);
    }

    @Test
    public void testBrokenConfig() {
        TIPConfig tc = new TIPConfig();
        tc.buildResponse();
        String configJSON = "{\"sooo\" : \"broke\"}";
        boolean actual = TIPSchemas.validateJsonAgainstSchema(tc.getClass(), configJSON);
        assertFalse("configSchema catches broken config", actual);
    }

    @Test
    public void validateDistanceSchema() {
        Map<String, Object> origin = new HashMap<>();
        origin.put("latitude", "40.576179");
        origin.put("longitude", "-105.080773");
        origin.put("name", "Oval, Colorado State University, Fort Collins, Colorado, USA");
        Map<String, Object> destination = new HashMap<>();
        destination.put("latitude", "40.576179");
        destination.put("longitude", "-105.080773");
        destination.put("name", "Oval, Colorado State University, Fort Collins, Colorado, USA");

        Gson gson = new Gson();
        TIPDistance td = new TIPDistance(4, origin, destination, 3958);
        String distanceJSON = gson.toJson(td);

        boolean actual = TIPSchemas.validateJsonAgainstSchema(td.getClass(), distanceJSON);
        assertTrue("distanceSchema allows a valid JSONObject", actual);
    }

    @Test
    public void testBrokenDistance() {
        String distanceJSON = "{\"not\" : \"quite\"}";

        boolean actual = TIPSchemas.validateJsonAgainstSchema(TIPDistance.class, distanceJSON);
        assertFalse("distanceSchema catches broken JSONObject", actual);
    }

    @Test
    public void validateItinerarySchema() {
        Map<String, Object> options = new HashMap<>();
        options.put("earthRadius", "3958");
        List<Map> places = new ArrayList<>();
        TIPItinerary ti = new TIPItinerary(4, options, places, "none");

        Gson gson = new Gson();
        String itineraryJSON = gson.toJson(ti);
        boolean actual = TIPSchemas.validateJsonAgainstSchema(TIPItinerary.class, itineraryJSON);
        assertTrue("itinerarySchema allows a valid JSONObject", actual);
    }

    @Test
    public void testBrokenItinerary() {
        String itineraryJSON = "{\"requestType\" : \"itinerary\"}";

        boolean actual = TIPSchemas.validateJsonAgainstSchema(TIPItinerary.class, itineraryJSON);
        assertFalse("itinerarySchema catches broken JSONObject", actual);
    }

    @Test
    public void validateFindSchema() {
        List<Map> places = new ArrayList<>();
        TIPFind tf = new TIPFind("find",4, "lkajsdljfljdsalkfj", 0, 0, places);
        Gson gson = new Gson();
        String findJSON = gson.toJson(tf);

        boolean actual = TIPSchemas.validateJsonAgainstSchema(TIPFind.class, findJSON);
        assertTrue("itinerarySchema allows a valid JSONObject", actual);
    }

    @Test
    public void validateFindSchemaWithNarrow() {
        String findJSON = "{\n" +
                "  \"requestType\"    : \"find\",\n" +
                "  \"requestVersion\" : 4,\n" +
                "  \"match\"          : \"fort collins\",\n" +
                "  \"narrow\"         : [{\"name\":\"type\", \"values\":[]}]\n" +
                "}\n";

//        boolean actual = TIPSchemas.validateJsonAgainstSchema(TIPFind.class, findJSON);
//        assertTrue("itinerarySchema allows narrow attribute", actual);
        TIPSchemas.loadSchemaForClass(TIPFind.class).validate(new JSONObject(findJSON));
    }

    @Test
    public void testBrokenFind() {
        String findJSON = "{\"billion\" : \"matches\"}";

        boolean actual = TIPSchemas.validateJsonAgainstSchema(TIPFind.class, findJSON);
        assertFalse("findSchema catches broken JSONObject", actual);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidClass() {
        String json = "";

        // this should fail
        TIPSchemas.validateJsonAgainstSchema(Integer.class, json);
    }
}
