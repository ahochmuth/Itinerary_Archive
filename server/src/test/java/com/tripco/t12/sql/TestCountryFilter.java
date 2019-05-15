package com.tripco.t12.sql;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import static org.junit.Assert.assertEquals;

public class TestCountryFilter {

    private List<String> countries = new ArrayList<>();

    @Test
    public void testGetName() {
        CountryFilter t = new CountryFilter();
        String name = t.getName();
        assertEquals("country", name);
    }

    @Test
    public void testGetSQL() {
        countries.add("Brazil");
        countries.add("Sudan");
        CountryFilter cf = new CountryFilter(countries);
        String expected = "(world.iso_country IN (SELECT country.id FROM country WHERE name IN (?, ?)))";
        assertEquals(expected, cf.getSQL());
    }

    @Test
    public void testFormatValues() {
        CountryFilter cf = new CountryFilter();
        JSONObject jo = new JSONObject("{\"name\": \"country\", \"values\": [\"Brazil\", \"Chile\"]}");
        List<String> actual = cf.formatValues(jo);
        List<String> expected = new ArrayList<>();
        expected.add("Brazil");
        expected.add("Chile");
        assertEquals(expected, actual);
    }

    @Test
    public void testGetValidValues() {
        CountryFilter cf = new CountryFilter();
        int actualLength = cf.getValidValues().size();
        int expecteLength = 204;
        assertEquals(expecteLength, actualLength);
    }
}
