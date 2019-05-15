package com.tripco.t12.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class CountryFilter extends SQLFilter {
    private static List<String> validValues;
    private List<String> selectedCountries;

    static  {
        validValues = formatValues(loadFilter("countries.json"));
    }

    public CountryFilter() {
        selectedCountries = new ArrayList<>();
    }

    public CountryFilter(List<String> countries) {
        selectedCountries = countries;
    }

    public static List<String> formatValues(JSONObject input) {
        JSONArray ja = (JSONArray)input.get("values");
        List<String> temp = new ArrayList<>();
        for (Object country: ja) {
            temp.add(country.toString());
        }
        return temp;
    }

    private static JSONObject loadFilter(final String name) {
        InputStream in = CountryFilter.class.getResourceAsStream("/" + name);
        return new JSONObject(new JSONTokener(in));
    }

    @Override
    public List<String> getValidValues() {
        return validValues;
    }

    @Override
    public String getName() { return "country";}

    @Override
    public String getSQL() {
        //If none selected search All
        if(selectedCountries.isEmpty()) {
            return "(TRUE)";
        }
        int count = selectedCountries.size();
        List<String> placeHolders = Collections.nCopies(count, "?");
        String queryString = String.join(", ", placeHolders);
        String query = "(world.iso_country IN (SELECT country.id FROM country WHERE name IN ("+ queryString +")))";
        return query;
    }

    @Override
    public void setParameters(SQLQuery parentQuery) throws SQLException {
        for (String country: selectedCountries) {
            parentQuery.setParameterString(country);
        }
    }


}
