package com.tripco.t12.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class FilterType extends SQLFilter {
    private static List<String> validTypes =
            Arrays.asList("airport", "heliport", "balloonport", "closed");
    private List<String> types;

    /**
     * Creates filter which matches anything.
     */
    public FilterType() {
        types = new ArrayList<>();
    }

    /**
     * Creates filter which matches the types supplied, or anything if the values
     * supplied are empty.
     * @param types What types should be matched
     */
    public FilterType(List<String> types) throws FilterException {
        validateValues(types);
        this.types = types;
    }

    private void validateValues(List<String> valuesParam) throws FilterException {
        for (String value : valuesParam) {
            if(!validTypes.contains(value)) {
                throw new FilterException(value + " not a valid type");
            }
        }

    }

    @Override
    public String getSQL() throws FilterException {
        // If values is empty, match anything
        if(types.isEmpty()) {
            return "(TRUE)";
        }

        List<String> typesMatched = new ArrayList<>();
        for (String value : types) {
            switch (value) {
            case "airport":
                typesMatched.add("small_airport");
                typesMatched.add("medium_airport");
                typesMatched.add("large_airport");
                break;
            case "heliport":
                typesMatched.add("heliport");
                break;
            case "balloonport":
                typesMatched.add("balloonport");
                break;
            case "closed":
                typesMatched.add("closed");
                break;
            default:
                throw new FilterException("invalid type");
            }
        }
        // Must have at least one type to match
        assert !typesMatched.isEmpty();
        List<String> matchSqlClauses = typesMatched.stream()
                .map(this::formatTypeAsSqlClause).collect(Collectors.toList());

        String combinedClause = String.join(" OR ", matchSqlClauses);

        // put parens around the whole clause
        return "(" + combinedClause + ")";
    }

    @Override
    public void setParameters(SQLQuery parentQuery) {

    }

    private String formatTypeAsSqlClause(String type) {
        return String.format("type = \"%s\"", type);
    }

    @Override
    public String getName() {
        return "type";
    }

    @Override
    public List<String> getValidValues() {
        return validTypes;
    }
}
