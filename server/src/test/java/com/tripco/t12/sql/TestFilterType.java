package com.tripco.t12.sql;

import com.tripco.t12.sql.FilterException;
import com.tripco.t12.sql.FilterType;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestFilterType {
    @Test
    public void filterEmpty() throws FilterException {
        FilterType ft = new FilterType();
        String actualSql = ft.getSQL();
        String expectedSql = "(TRUE)";
        assertEquals(expectedSql, actualSql);
    }
    @Test
    public void filterAirport() throws FilterException {
        List<String> types = Arrays.asList("airport");
        FilterType ft = new FilterType(types);
        String actualSql = ft.getSQL();
        String expectedSql = "(type = \"small_airport\" " +
                "OR type = \"medium_airport\" " +
                "OR type = \"large_airport\")";
        assertEquals(expectedSql, actualSql);
    }
    @Test
    public void filterBalloonPort() throws FilterException {
        List<String> types = Arrays.asList("balloonport");
        FilterType ft = new FilterType(types);
        String actualSql = ft.getSQL();
        String expectedSql = "(type = \"balloonport\")";
        assertEquals(expectedSql, actualSql);
    }
    @Test
    public void filterHeliport() throws FilterException {
        List<String> types = Arrays.asList("heliport");
        FilterType ft = new FilterType(types);
        String actualSql = ft.getSQL();
        String expectedSql = "(type = \"heliport\")";
        assertEquals(expectedSql, actualSql);
    }
    @Test
    public void filterClosed() throws FilterException {
        List<String> types = Arrays.asList("closed");
        FilterType ft = new FilterType(types);
        String actualSql = ft.getSQL();
        String expectedSql = "(type = \"closed\")";
        assertEquals(expectedSql, actualSql);
    }
    @Test
    public void filterAll() throws FilterException {
        List<String> types = Arrays.asList("airport", "heliport", "balloonport", "closed");
        FilterType ft = new FilterType(types);
        String actualSql = ft.getSQL();
        String expectedSql = "(type = \"small_airport\" " +
                "OR type = \"medium_airport\" " +
                "OR type = \"large_airport\" " +
                "OR type = \"heliport\" " +
                "OR type = \"balloonport\" " +
                "OR type = \"closed\")";
        assertEquals(expectedSql, actualSql);
    }
    @Test(expected = FilterException.class)
    public void attemptSqlInjection() throws FilterException {
        List<String> types = Arrays.asList("\" OR 1=\"");
        FilterType ft = new FilterType(types);
        String actualSql = ft.getSQL();
        String expectedSql = "";
        assertEquals(expectedSql, actualSql);
    }
    @Test(expected = FilterException.class)
    public void invalid() throws FilterException {
        List<String> types = Arrays.asList("banana");
        FilterType ft = new FilterType(types);
        ft.getSQL();
    }
    @Test
    public void hasName() {
        FilterType ft = new FilterType();
        ft.getName();
        ft.getValidValues();
    }
    @Test(expected = FilterException.class)
    public void insertInvalidValue() throws NoSuchFieldException, IllegalAccessException, FilterException {
        FilterType ft = new FilterType();

        // Use reflection to change internal field
        Field types = ft.getClass().getDeclaredField("types");
        types.setAccessible(true);

        // This triggers a different piece of validation code
        List<String> newTypes = Arrays.asList("banana");
        types.set(ft, newTypes);
        types.setAccessible(false);

        ft.getSQL();
    }
}
