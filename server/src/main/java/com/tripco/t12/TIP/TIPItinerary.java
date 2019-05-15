package com.tripco.t12.TIP;

import com.tripco.t12.misc.GreatCircleDistance;
import com.tripco.t12.misc.Location;
import com.tripco.t12.misc.OptimizeTour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.Double;


public class TIPItinerary extends TIPHeader {
    private Map options;
    private List<Map> places;
    private List<Long> distances;

    private final transient Logger log = LoggerFactory.getLogger(TIPItinerary.class);

    public TIPItinerary(int version, Map options, List<Map> places, String optimization) {
        this();
        this.requestVersion = version;
        this.options = options;
        this.places = places;
        this.distances = new ArrayList<>();
        this.options.put("optimization", optimization);
    }

    public TIPItinerary() {
        this.requestType = "itinerary";
    }


    @Override
    public void buildResponse() throws ClientSideException {
        requestVersion = 5;

        Double earthRadius = getEarthRadius();
        String optimizationLevel;
        optimizationLevel = (String) options.get("optimization");
        if(optimizationLevel == null) {
            // If no optimization level is sent, that is technically violation of
            // TIP spec. However, just assume no optimization
            optimizationLevel = "none";
        }

        // If optimizations are turned on, optimize
        if (!optimizationLevel.equals("none")) {
            List<Location> optimized = OptimizeTour.optimizeTour(this.getPlacesAsLocations(), earthRadius, optimizationLevel);
            setPlacesAsLocations(optimized);
        }

        // Find distances between each place
        distances = new ArrayList<>();
        for (int i = 0; i < places.size(); i++) {
            double lat1 = parseLat(places.get(i));
            double lon1 = parseLong(places.get(i));

            double lat2 = parseLat(places.get(0));
            double lon2 = parseLong(places.get(0));

            if (i < (places.size() - 1)) {
                lat2 = parseLat(places.get(i + 1));
                lon2 = parseLong(places.get(i + 1));
            }

            long dist = GreatCircleDistance.haversine(lat1, lon1, lat2, lon2, earthRadius);
            distances.add(dist);
        }
        log.trace("buildResponse -> {}", this);
    }

    private double parseLat(Map map) {
        double lat = Double.parseDouble(map.get("latitude").toString());
        if (lat > 90 || lat < -90) {
            throw new IllegalArgumentException();
        }
        return lat;
    }

    private double parseLong(Map map) {
        double lon = Double.parseDouble(map.get("longitude").toString());
        if (lon > 180 || lon < -180) {
            throw new IllegalArgumentException();
        }
        return lon;
    }

    public List<Map> getPlaces() {
        return places;
    }

    public List<Location> getPlacesAsLocations() {
        List<Location> ret = new ArrayList<>();
        for (Map place : places) {
            ret.add(new Location(place));
        }
        return ret;
    }

    public void setPlacesAsLocations(List<Location> locations) {
        places = new ArrayList<>();
        for (Location location : locations) {
            places.add(location.getOriginalMap());
        }
    }

    public double getEarthRadius() {
        return Double.parseDouble(options.get("earthRadius").toString());
    }

    public List<Long> getDistances() {
        return distances; 
    }

    String getOptimization() { 
        return options.get("optimization").toString(); 
    }

    @Override
    public String toString() {
        return "TIPItinerary{" +
                ", distances=" + distances +
                ", places=" + places +
                ", options" + options +
                ", requestVersion=" + requestVersion +
                ", requestType='" + requestType + '\'' +
                "}";
    }


}
