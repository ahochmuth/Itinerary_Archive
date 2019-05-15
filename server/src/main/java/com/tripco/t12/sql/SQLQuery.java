package com.tripco.t12.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SQLQuery {
    // Timeout in seconds
    private static final int SQL_TIMEOUT = 10;
    private static final Logger log = LoggerFactory.getLogger(SQLQuery.class);
    private static String serverURL;
    private static String userSQL;
    private static String passwordSQL;

    static {
        // Set server URL, user, password at startup
        autoDetectSettings();
    }

    private PreparedStatement statement;
    private int parameterIndex = 1;
    Connection server;

    /**
     * Create an SQLQuery connection. The SQLQuery object can be reused for multiple queries,
     * but cannot be used concurrently.
     * @throws SQLException if SQLQuery cannot connect to the server.
     */
    public SQLQuery() throws SQLException {
        log.debug("Connecting to SQL server {} as user {} with password of length {}",
                serverURL, userSQL, passwordSQL.length());
        DriverManager.setLoginTimeout(SQL_TIMEOUT);
        server = DriverManager.getConnection(serverURL, userSQL, passwordSQL);
    }

    /**
     * This method is run once at startup, to set the database host and user based upon
     * environment variables.
     */
    private static void autoDetectSettings() {
        int timeoutMs = SQL_TIMEOUT * 1000;
        String timeoutSettings = String.format("?connectTimeout=%d&socketTimeout=%d",
                timeoutMs, timeoutMs);

        if(isDevelopment()) {
            serverURL = "jdbc:mysql://127.0.0.1/cs314" + timeoutSettings;
            userSQL = "cs314-db";
            passwordSQL = "eiK5liet1uej";
        } else if(isTravis()) {
            serverURL = "jdbc:mysql://127.0.0.1/cs314" + timeoutSettings;
            userSQL = "root";
            passwordSQL = "";
        } else {
            // If not development or travis, assume production
            serverURL = "jdbc:mysql://faure.cs.colostate.edu/cs314" + timeoutSettings;
            userSQL = "cs314-db";
            passwordSQL = "eiK5liet1uej";
        }
    }

    private static boolean isTravis() {
        String travisEnv = System.getenv("TRAVIS");
        return travisEnv != null && travisEnv.equals("true");
    }

    private static boolean isDevelopment() {
        String developmentEnv = System.getenv("CS314_ENV");
        return developmentEnv != null && developmentEnv.equals("development");
    }

    /**
     * This method checks if we can get a connection to the database. Mostly useful for debugging.
     * @return true if a connection can be established with the database, false otherwise.
     */
    public static boolean testSqlConnection() {
        try {
            SQLQuery query = new SQLQuery();

            // This query is for checking if the server is up and we can connect to it
            String testServerQuery = "SELECT count(*) FROM world";
            query.statement = query.server.prepareStatement(testServerQuery);
            query.statement.setQueryTimeout(SQL_TIMEOUT);

            ResultSet resultSet = query.statement.executeQuery();

            // SELECT COUNT(*) FROM ... will return one row, and one column, representing
            // how many rows there are in the table
            resultSet.next();
            int rowCount = resultSet.getInt(1);

            // Ensure that the table is not empty. If there is at least one row,
            // declare success.
            return rowCount > 0;
        } catch (SQLException e) {
            // That didn't work
            // Therefore, probably no SQL query will work
            log.error("Cannot connect to SQL server! Settings are:"
                            + "SQL server {}\n"
                            + "user {}\n"
                            + "with password of length {}\n",
                    serverURL, userSQL, passwordSQL.length());
            return false;
        }
    }

    private void setSearch(PreparedStatement statement, String searchString) throws SQLException {
        // In SQL, you can use LIKE to do a full-text search.
        // If you write `SELECT ... WHERE id LIKE "%airport%"`, you get all rows where airport
        // occurs somewhere in id.
        String wildcardsAdded = "%" + searchString + "%";
        // Our search string appears five times in the query, (for both query types)
        // so we must set all five uses.
        for (int i = 0; i < 5; i++) {
            setParameterString(wildcardsAdded);
        }
    }

    public void setParameterString(String str) throws SQLException {
        assert statement != null;
        statement.setString(parameterIndex, str);
        parameterIndex++;
    }

    private void setParameterInt(int integer) throws SQLException {
        assert statement != null;
        statement.setInt(parameterIndex, integer);
        parameterIndex++;
    }

    /**
     * Count number of matches of the search string.
     * @param searchString The string to search for in the database.
     * @return the number of matches
     * @throws SQLException if any SQL operation fails.
     */
    public int sqlSearchCount(String searchString, List<SQLFilter> filters) throws SQLException, FilterException {
        resetQuery();
        String countQuery = "SELECT count(*) "
                + "FROM world "
                + "INNER JOIN country ON world.iso_country=country.id "
                + "INNER JOIN region ON world.iso_region=region.id "
                + "INNER JOIN continent ON world.continent=continent.id  WHERE "
                + "((world.id LIKE ?) OR "
                + "(world.name LIKE ?) OR "
                + "(municipality LIKE ?) OR "
                + "(region.name LIKE ?) OR "
                + "(country.name LIKE ?)) "  + getSqlForFilters(filters) +  ";";
        statement = server.prepareStatement(countQuery);
        statement.setQueryTimeout(SQL_TIMEOUT);
        setSearch(statement, searchString);
        setFilterParameters(filters);
        ResultSet resultSet = statement.executeQuery();

        // This always returns one row, and one column
        resultSet.next();
        return resultSet.getInt(1);
    }

    /**
     * Get list of matches of the search string. Limit list to the first 'limit'
     * matches.
     * @param searchString The string to search for in the database.
     * @param limit the maximum number of results to return, or 0 if there is no limit.
     * @return List of matches. Each element is a Map that has only the attributes
     *     in placeAttributes.
     * @throws SQLException if any SQL operation fails.
     */
    public List<Map> sqlSearchGet(String searchString, int limit, List<SQLFilter> filters) throws SQLException, FilterException {
        resetQuery();
        // From tip.md:
        // "A find request with "match":"fort" should find all locations with the
        // string "fort" in the name, municipality, or other identifying columns."

        // Therefore, we need to search id, name, and municipality, and remove duplicates.
        // The easiest way to do that is to get the SQL server to do it for us. That's why
        // this query is so long.
        String getQuery = "SELECT world.name,latitude,longitude,world.id,municipality,altitude,"
                + "country.name AS country,"
                + "region.name AS region,"
                + "continent.name AS continent "
                + "FROM world "
                + "INNER JOIN country ON world.iso_country=country.id "
                + "INNER JOIN region ON world.iso_region=region.id "
                + "INNER JOIN continent ON world.continent=continent.id  WHERE "
                + "((world.id LIKE ?) OR "
                + "(world.name LIKE ?) OR "
                + "(municipality LIKE ?) OR "
                + "(region.name LIKE ?) OR "
                + "(country.name LIKE ?)) "  + getSqlForFilters(filters) +  " LIMIT ?;";

        statement = server.prepareStatement(getQuery);

        statement.setQueryTimeout(SQL_TIMEOUT);

        if(limit == 0) {
            limit = Integer.MAX_VALUE;
        }

        List<Map> returnValue = new ArrayList<>();
        setSearch(statement, searchString);
        setFilterParameters(filters);
        setParameterInt(limit);
        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData columns = resultSet.getMetaData();

        while(resultSet.next()) {
            returnValue.add(resultToPlaceMap(resultSet, columns));
        }

        return returnValue;
    }

    private String getSqlForFilters(List<SQLFilter> filters) throws FilterException {
        if(filters == null) return "";
        StringBuilder sb = new StringBuilder();
        for (SQLFilter filter : filters) {
            sb.append(" AND ");
            sb.append(filter.getSQL());
        }
        return sb.toString();
    }

    /**
     * Convert database row to Map. This sets the keys based on the column name,
     * and the value based on the row under the database cursor. (See ResultSet documentation.)
     * @param resultSet The database cursor, returned by PreparedStatement.executeQuery()
     * @param columns The metadata object containing the column names.
     * @return Map object, with placeAttributes as keys
     * @throws SQLException If getting the values or keys fails.
     */
    private Map resultToPlaceMap(ResultSet resultSet, ResultSetMetaData columns)
            throws SQLException {
        Map<String, String> returnValue = new HashMap<>();

        // ResultSet is 1-indexed, not 0-indexed. Ick.
        for(int i = 1; i <= columns.getColumnCount(); i++) {
            String columnName = columns.getColumnLabel(i);
            String value = resultSet.getString(i);
            returnValue.put(columnName, value);
        }
        return returnValue;
    }

    private void resetQuery() {
        statement = null;
        parameterIndex = 1;
    }

    private void setFilterParameters(List<SQLFilter> filters) throws SQLException{
        if (filters != null) {
            for (SQLFilter filter : filters) {
                filter.setParameters(this);
            }
        }
    }

}
