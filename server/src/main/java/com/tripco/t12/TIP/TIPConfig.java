package com.tripco.t12.TIP;

import com.tripco.t12.sql.SQLFilter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class defines the Config response that provides the client
 * with server specific configuration information.
 * <p>
 * When used with restful API services,
 * An object is created from the request JSON by the MicroServer using GSON.
 * The buildResponse method is called to set the configuration information.
 * The MicroServer constructs the response JSON from the object using GSON.
 * <p>
 * When used for testing purposes,
 * An object is created using the constructor below.
 * The buildResponse method is called to set the configuration information.
 * The getDistance method is called to obtain the distance value for comparisons.
 */
public class TIPConfig extends TIPHeader {
    private String serverName;
    private List<String> placeAttributes;
    private List<String> optimizations;
    private List<Map> filters;

    private final transient Logger log = LoggerFactory.getLogger(TIPConfig.class);

    public TIPConfig() {}


    @Override
    public void buildResponse() {
        this.requestType = "config";
        this.requestVersion = 5;
        this.serverName = "t12 Team Spirit";
        this.placeAttributes = Arrays.asList("name", "latitude", "longitude", "id", "municipality", "region", "country", "continent", "altitude");
        this.optimizations = Arrays.asList("none", "short", "shorter");
        this.filters = SQLFilter.getAllFiltersAsMaps();
    }


    public String getServerName() {
        return this.serverName;
    }

    public String getType() {
        return this.requestType;
    }

    public int getVersion() {
        return this.requestVersion;
    }

    public List<String> getPlaceAttributes() {
        return this.placeAttributes;
    }

    public List<String> getOptimizations() { 
        return this.optimizations; 
    }

    public List<Map> getFilters() { return this.filters; }

    @Override
    public String toString() {
        return "TIPConfig{" 
                + "serverName='" + serverName + '\'' 
                + ", placeAttributes=" + placeAttributes
                + ", optimizations=" + optimizations
                + ", requestVersion=" + requestVersion
                + ", requestType='" + requestType + '\''
                + "}";
    }
}
