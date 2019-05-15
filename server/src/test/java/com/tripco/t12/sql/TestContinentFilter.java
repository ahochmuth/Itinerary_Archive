package com.tripco.t12.sql;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestContinentFilter {
    private List<String> continents = new ArrayList<>();

    @Test
    public void testGetName() {
        ContinentFilter t = new ContinentFilter();
        String name = t.getName();
        assertEquals("continent", name);
    }

    @Test
    public void testGetSQL() {
        continents.add("Asia");
        continents.add("Europe");
        ContinentFilter cf = new ContinentFilter(continents);
        String expected = "(world.iso_continent IN (SELECT continent.id FROM continent WHERE name IN (?, ?)))";
        assertEquals(expected, cf.getSQL());
    }

    @Test
    public void testGetValidValues() {
        ContinentFilter cf = new ContinentFilter();
        int actualLength = cf.getValidValues().size();
        int expectedLength = 7;
        assertEquals(expectedLength, actualLength);
    }
}
