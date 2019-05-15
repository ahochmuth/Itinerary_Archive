package com.tripco.t12.TIP;

import com.google.gson.Gson;
import com.tripco.t12.TIP.TIPFind;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Ignore("integration-only")
public class TestTIPFind {
    @Test
    public void searchNoResults() throws ClientSideException {
        TIPFind f = new TIPFind("lkajsdljfljdsalkfj", 0);
        f.buildResponse();
        int expectedResults = 0;
        int actualResults = f.getFound();
        assertEquals(expectedResults, actualResults);
    }
    @Test
    public void searchFC() throws ClientSideException {
        TIPFind f = new TIPFind("fort collins", 10);
        f.buildResponse();

        int actualPlaceCount = f.getPlaces().size();
        int expectedPlaceCount = 10;
        assertEquals(expectedPlaceCount, actualPlaceCount);

        // even if we only ask for 10 results, if there are more, it should give the full count
        int expectedResults = 13;
        int actualResults = f.getFound();
        assertEquals(expectedResults, actualResults);
    }
    @Test
    public void searchFCWithWildcards() throws ClientSideException {
        // non alphanumerics should be replaced with wildcards
        TIPFind f = new TIPFind("f!)t collins", 1);
        f.buildResponse();

        int actualPlaceCount = f.getPlaces().size();
        int expectedPlaceCount = 1;
        assertEquals(expectedPlaceCount, actualPlaceCount);

        int expectedResults = 13;
        int actualResults = f.getFound();
        assertEquals(expectedResults, actualResults);
    }
    @Test
    public void searchRanchAirstrip() throws ClientSideException {
        TIPFind f = new TIPFind("ranch airstrip", 10);
        f.buildResponse();

        int actualPlaceCount = f.getPlaces().size();
        int expectedPlaceCount = 10;
        assertEquals(expectedPlaceCount, actualPlaceCount);

        int expectedResults = 28;
        int actualResults = f.getFound();
        assertEquals(expectedResults, actualResults);
    }
    @Test
    public void searchRanchFilter() throws ClientSideException {
        String json = "{\n" +
                "  \"requestType\"    : \"find\",\n" +
                "  \"requestVersion\" : 3,\n" +
                "  \"match\"          : \"fort collins\",\n" +
                "  \"limit\"          : 0,\n" +
                "  \"narrow\"         : [{\"name\": \"type\", \"values\": [\"airport\"]}],\n" +
                "  \"found\"          : 0,\n" +
                "  \"places\"         : []\n" +
                "}\n";
        TIPFind f = (TIPFind) new Gson().fromJson(json, TIPFind.class);
        f.buildResponse();

        int actualPlaceCount = f.getPlaces().size();
        int expectedPlaceCount = 5;
        assertEquals(expectedPlaceCount, actualPlaceCount);

        int expectedResults = 5;
        int actualResults = f.getFound();
        assertEquals(expectedResults, actualResults);
    }
}
