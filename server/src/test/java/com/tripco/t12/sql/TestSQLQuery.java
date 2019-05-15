package com.tripco.t12.sql;

import com.tripco.t12.sql.FilterException;
import com.tripco.t12.sql.SQLQuery;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore("integration-only")
public class TestSQLQuery {
    private static List<Map> fc = new ArrayList<>();
    private static List<Map> actualFCQuery;
    private static boolean ranFCSearch = false;
    public static Map createPlace(String name, String latitude, String longitude, String id, String municipality, String altitude, String country, String region, String continent) {
        Map returnValue = new HashMap();
        returnValue.put("name", name);
        returnValue.put("latitude", latitude);
        returnValue.put("longitude", longitude);
        returnValue.put("id", id);
        returnValue.put("municipality", municipality);
        returnValue.put("altitude", altitude);
        returnValue.put("country", country);
        returnValue.put("region", region);
        returnValue.put("continent", continent);
        return returnValue;
    }
    @BeforeClass
    public static void initAirports() {
        fc.add(createPlace("Geo-Seis Helicopters Heliport","40.5899009705","-105.04599762","0CO4","Fort Collins","4935","United States","Colorado","North America"));
        fc.add(createPlace("Century Helicopters Heliport","40.5854988098","-105.040000916","0CO7","Fort Collins","4935","United States","Colorado","North America"));
        fc.add(createPlace("Heli-Support Ii Heliport","40.58359909057617","-106.98500061035156","2CO0","Fort Collins","4935","United States","Colorado","North America"));
        fc.add(createPlace("William T Browder Heliport","40.65829849243164","-104.95099639892578","50CO","Fort Collins","5200","United States","Colorado","North America"));
        fc.add(createPlace("Wkr Airport","40.52080154418945","-104.96700286865234","65CO","Fort Collins","4840","United States","Colorado","North America"));
        fc.add(createPlace("Hat-Field STOLport","40.51029968261719","-105.0009994506836","6CO4","Fort Collins","4885","United States","Colorado","North America"));
        fc.add(createPlace("Poudre Valley Hospital Heliport","40.57160186767578","-105.05599975585938","CD07","Fort Collins","4960","United States","Colorado","North America"));
        fc.add(createPlace("North Arrow Heliport","40.59109878540039","-105.0479965209961","CD46","Fort Collins","4935","United States","Colorado","North America"));
        fc.add(createPlace("Yankee Field","40.634700775146484","-104.99099731445312","CO53","Fort Collins","5050","United States","Colorado","North America"));
        fc.add(createPlace("Christman Field","40.597198486328125","-105.14399719238281","CO55","Fort Collins","5160","United States","Colorado","North America"));
        fc.add(createPlace("Heli-Support Heliport","40.583900451660156","-105.03500366210938","CO91","Fort Collins","4935","United States","Colorado","North America"));
        fc.add(createPlace("Fort Collins Downtown Airport","40.5882987976","-105.041999817","K3V5",null,"4939","United States","Colorado","North America"));
        fc.add(createPlace("Fort Collins Loveland Municipal Airport","40.4518013","-105.011001587","KFNL","Fort Collins/Loveland","5016","United States","Colorado","North America"));
    }

    public void initFCSearch() {
        if(ranFCSearch) return;
        ranFCSearch = true;
        try {
            SQLQuery sqlQuery = new SQLQuery();
            actualFCQuery = sqlQuery.sqlSearchGet("fort collins", 20, null);
        }catch (SQLException | FilterException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testFortCollinsCount() throws SQLException, FilterException {
        SQLQuery sqlQuery = new SQLQuery();
        int actual = sqlQuery.sqlSearchCount("fort collins", null);
        int expected = 13;
        assertEquals(expected, actual);
    }

    @Test
    public void testNoLimit() throws SQLException, FilterException {
        SQLQuery sqlQuery = new SQLQuery();
        List<Map> actual = sqlQuery.sqlSearchGet("fort collins", 0, null);
        int expectedSize = 13;
        int actualSize = actual.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testFortCollinsSearchItem_0() {
        initFCSearch();
        Map actualItem = actualFCQuery.get(0);
        Map expectedItem = fc.get(0);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testFortCollinsSearchItem_1() {
        initFCSearch();
        Map actualItem = actualFCQuery.get(1);
        Map expectedItem = fc.get(1);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testFortCollinsSearchItem_2() {
        initFCSearch();
        Map actualItem = actualFCQuery.get(2);
        Map expectedItem = fc.get(2);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testFortCollinsSearchItem_3() {
        initFCSearch();
        Map actualItem = actualFCQuery.get(3);
        Map expectedItem = fc.get(3);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testFortCollinsSearchItem_4() {
        initFCSearch();
        Map actualItem = actualFCQuery.get(4);
        Map expectedItem = fc.get(4);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testFortCollinsSearchItem_5() {
        initFCSearch();
        Map actualItem = actualFCQuery.get(5);
        Map expectedItem = fc.get(5);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testFortCollinsSearchItem_6() {
        initFCSearch();
        Map actualItem = actualFCQuery.get(6);
        Map expectedItem = fc.get(6);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testFortCollinsSearchItem_7() {
        initFCSearch();
        Map actualItem = actualFCQuery.get(7);
        Map expectedItem = fc.get(7);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testFortCollinsSearchItem_8() {
        initFCSearch();
        Map actualItem = actualFCQuery.get(8);
        Map expectedItem = fc.get(8);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testFortCollinsSearchItem_9() {
        initFCSearch();
        Map actualItem = actualFCQuery.get(9);
        Map expectedItem = fc.get(9);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testFortCollinsSearchItem_10() {
        initFCSearch();
        Map actualItem = actualFCQuery.get(10);
        Map expectedItem = fc.get(10);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testFortCollinsSearchItem_11() {
        initFCSearch();
        Map actualItem = actualFCQuery.get(11);
        Map expectedItem = fc.get(11);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testFortCollinsSearchItem_12() {
        initFCSearch();
        Map actualItem = actualFCQuery.get(12);
        Map expectedItem = fc.get(12);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testConnectionSetup() {
        assertTrue(SQLQuery.testSqlConnection());
    }


}
