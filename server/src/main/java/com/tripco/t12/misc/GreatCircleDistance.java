package com.tripco.t12.misc;

import java.lang.Math;

/** Determines the distance between geographic coordinates.
 * formula found at https://rosettacode.org/wiki/Haversine_formula
 */
public abstract class GreatCircleDistance {

    public static long haversine(double lat1, double lon1, double lat2, double lon2, double earthRadius) {
        if((lat1 == lat2) && (lon1 == lon2)){
            return 0;
        }
        else {
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            lat1 = Math.toRadians(lat1);
            lat2 = Math.toRadians(lat2);

            double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
            double c = 2 * Math.asin(Math.sqrt(a));
            double unrounded = earthRadius * c;
            return Math.round(unrounded);
        }
    }

    public static long distance(Location origin, Location dest, double earthRadius) {
        return haversine(origin.latitude, origin.longitude, dest.latitude, dest.longitude, earthRadius);
    }
}
