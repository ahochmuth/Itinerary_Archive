package com.tripco.t12.misc;

import java.util.Map;

public class Location {
    Map originalMap;
    double latitude;
    double longitude;

    public Location(Map originalMap) {
        this.originalMap = originalMap;
        String latitudeString = (String) this.originalMap.get("latitude");
        String longitudeString = (String) this.originalMap.get("longitude");
        latitude = Double.parseDouble(latitudeString);
        longitude = Double.parseDouble(longitudeString);
    }

    public Map getOriginalMap() {
        return originalMap;
    }
}
