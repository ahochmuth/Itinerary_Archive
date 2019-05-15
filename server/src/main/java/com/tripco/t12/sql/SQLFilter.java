package com.tripco.t12.sql;

import com.tripco.t12.TIP.TIPFind;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class SQLFilter {
    public abstract String getName();
    public abstract List<String> getValidValues();
    public abstract String getSQL() throws FilterException;
    public abstract void setParameters(SQLQuery parentQuery) throws SQLException;

    /**
     * Given a name and a list of values, choose what concrete type of SQLFilter to create.
     * @param name name of the filter
     * @param values values to give to filter constuctor
     * @return the constructed SQL filter
     * @throws FilterException if the client asked for a filter that does not exist
     */
    private static SQLFilter constructFilterFromName(String name, List<String> values) throws FilterException {
        switch(name) {
            case "type":
                return new FilterType(values);
            case "country":
                return new CountryFilter(values);
            case "continent":
                return new ContinentFilter(values);
            default:
                throw new FilterException("unknown filter name");
        }
    }

    /**
     * Makes a list of Maps into a list of SQLFilters, which simplifies code in SQLQuery
     * @param narrow parameter from TIPFind, which comes from JSON object
     * @return list of filters
     * @throws FilterException if the client fucked it up
     */
    public static List<SQLFilter> createFiltersFromJSON(List<TIPFind.Filter> narrow) throws FilterException {
        if(narrow == null) return null;
        List<SQLFilter> filters = new ArrayList();
        List<String> values;
        for (TIPFind.Filter filter : narrow) {
            String name;
            try {
                name = filter.getName();
                values = filter.getValues();
            } catch(NullPointerException e) {
                throw new FilterException("name or value should not be null");
            }
            filters.add(constructFilterFromName(name, values));
        }
        return filters;
    }

    /**
     * Get all available SQL filters. If you add a new SQLFilter, you must modify this method.
     * @return All supported SQL filters
     */
    private static List<SQLFilter> getAllFilters() {
        return Arrays.asList(new FilterType(), new CountryFilter(), new ContinentFilter());
    }

    /**
     * Convert all filters into maps that can be sent in a JSON response.
     * @return a List of Maps suitable for a config object
     */
    public static List<Map> getAllFiltersAsMaps() {
        List<Map> ret = new ArrayList<>();
        for (SQLFilter filter : getAllFilters()) {
            Map<String, Object> filterMap = new HashMap<>();
            filterMap.put("name", filter.getName());
            filterMap.put("values", filter.getValidValues());
            ret.add(filterMap);
        }
        return ret;
    }
}
