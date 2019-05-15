package com.tripco.t12.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ContinentFilter extends SQLFilter {
    private static List<String> validValues =
            Arrays.asList("Africa", "Antarctica", "Asia", "Australia",
                    "Europe", "North America", "South America");
    private List<String> selectedContinents;

    public ContinentFilter() {
        selectedContinents = new ArrayList<>();
    }

    public ContinentFilter(List<String> continents) {
        selectedContinents = continents;
    }


    @Override
    public List<String> getValidValues() {
        return validValues;
    }

    @Override
    public String getName() {
        return "continent";
    }

    @Override
    public String getSQL() {
        //If none selected search All
        if(selectedContinents.isEmpty()) {
            return "(TRUE)";
        }
        int count = selectedContinents.size();
        List<String> placeHolders = Collections.nCopies(count, "?");
        String queryString = String.join(", ", placeHolders);
        String query = "(world.iso_continent IN (SELECT continent.id " +
                "FROM continent WHERE name IN ("+ queryString +")))";
        return query;
    }

    @Override
    public void setParameters(SQLQuery parentQuery) throws SQLException {
        for (String continent: selectedContinents) {
            parentQuery.setParameterString(continent);
        }
    }
}
