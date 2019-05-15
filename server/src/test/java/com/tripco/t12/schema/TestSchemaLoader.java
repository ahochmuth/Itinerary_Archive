package com.tripco.t12.schema;

import com.tripco.t12.TIP.TIPConfig;
import com.tripco.t12.TIP.TIPDistance;
import com.tripco.t12.TIP.TIPFind;
import com.tripco.t12.TIP.TIPItinerary;
import org.everit.json.schema.Schema;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestSchemaLoader {
    @Test
    public void testConfigLoad() {
        Schema s = TIPSchemas.loadSchemaForClass(TIPConfig.class);
        assertNotNull("Expecting non-null config schema", s);
    }

    @Test
    public void testDistanceLoad() {
        Schema s = TIPSchemas.loadSchemaForClass(TIPDistance.class);
        assertNotNull("Expecting non-null distance schema", s);
    }

    @Test
    public void testFindgLoad() {
        Schema s = TIPSchemas.loadSchemaForClass(TIPFind.class);
        assertNotNull("Expecting non-null find schema", s);
    }

    @Test
    public void testItineraryLoad() {
        Schema s = TIPSchemas.loadSchemaForClass(TIPItinerary.class);
        assertNotNull("Expecting non-null itinerary schema", s);
    }
}
