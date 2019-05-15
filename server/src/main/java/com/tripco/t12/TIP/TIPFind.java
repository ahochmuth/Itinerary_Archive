package com.tripco.t12.TIP;

import com.tripco.t12.sql.FilterException;
import com.tripco.t12.sql.SQLFilter;
import com.tripco.t12.sql.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TIPFind extends TIPHeader {
    private String match;
    private Integer limit;
    private int found;
    private List<Map> places;
    private List<Filter> narrow;

    private final static Logger log = LoggerFactory.getLogger(TIPFind.class);

    public TIPFind(String requestType, int requestVersion, String match, int limit, int found, List<Map> places) {
        this.requestType = requestType;
        this.requestVersion = requestVersion;
        this.match = match;
        this.limit = limit;
        this.found = found;
        this.places = places;
    }

    public TIPFind(String match, int limit) {
        this.match = match;
        this.limit = limit;
    }

    @Override
    public void buildResponse() throws ClientSideException {
        requestVersion = 5;
        requestType = "find";
        try {
            int limitNonNull = 0;
            if (limit != null) {
                limitNonNull = limit;
            }
            String matchWildcards = match.replaceAll("[^A-Za-z0-9]", "_");
            SQLQuery query = new SQLQuery();
            List<SQLFilter> filters = SQLFilter.createFiltersFromJSON(narrow);
            found = query.sqlSearchCount(matchWildcards, filters);
            places = query.sqlSearchGet(matchWildcards, limitNonNull, filters);
        } catch (SQLException e) {
            log.error("SQL failure", e);
            // Re-throw the exception to cause an error 500
            throw new RuntimeException(e);
        } catch (FilterException e) {
            // Filter is not well formed
            // Blame it on the client
            throw new ClientSideException();
        }
    }

    @Override
    public String toString() {
        return "TIPFind{" +
                "match='" + match + '\'' +
                ", limit=" + limit +
                ", found=" + found +
                ", places=" + places +
                ", requestVersion=" + requestVersion +
                ", requestType='" + requestType + '\'' +
                "}";
    }

    public int getFound() {
        return found;
    }

    public List<Map> getPlaces() {
        return places;
    }

    /**
     * This class is used to represent the 'narrow' field of a find request.
     */
    public class Filter {
        private String name;
        private List<String> values;

        /**
         * Construct a new Filter object
         * <p>
         * This constructor is not used by GSON. It is only used by test code.
         * @param name
         * @param values
         */
        public Filter(String name, List<String> values) {
            this.name = name;
            this.values = values;
        }

        /**
         * Get name field
         * @return name
         */
        public String getName() {
            if (name == null) {
                return "";
            }
            return name;
        }

        /**
         * Get values list
         * @return values
         */
        public List<String> getValues() {
            if (name == null) {
                return Arrays.asList();
            }
            return values;
        }
    }
}
