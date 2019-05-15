package com.tripco.t12.TIP;

import com.tripco.t12.misc.GreatCircleDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.lang.Double;
/** Defines the TIP distance object.
 *
 * For use with restful API services,
 * An object is created from the request JSON by the MicroServer using GSON.
 * The buildResponse method is called to determine the distance.
 * The MicroServer constructs the response JSON from the object using GSON.
 *
 * For unit testing purposes,
 * An object is created using the constructor below with appropriate parameters.
 * The buildResponse method is called to determine the distance.
 * The getDistance method is called to obtain the distance value for comparisons.
 *
 */
public class TIPDistance extends TIPHeader {
  private Map origin;
  private Map destination;
  private Double earthRadius;
  private Long distance;

  private final transient Logger log = LoggerFactory.getLogger(TIPDistance.class);


  public TIPDistance(int version, Map origin, Map destination, double earthRadius) {
    this();
    this.requestVersion = version;
    this.origin = origin;
    this.destination = destination;
    this.earthRadius = earthRadius;
    this.distance = 0L;
  }


  public TIPDistance() {
    this.requestType = "distance";
  }

  @Override
  public void buildResponse() {
    requestVersion = 5;
    double lat1 = parseLat(origin);
    double lon1 = parseLong(origin);
    double lat2 = parseLat(destination);
    double lon2 = parseLong(destination);

    long dist = GreatCircleDistance.haversine(lat1, lon1, lat2, lon2, earthRadius);

    this.distance = dist;
    log.trace("buildResponse -> {}", this);
  }

  private double parseLat(Map map){
    double lat = Double.parseDouble(map.get("latitude").toString());
    if(lat > 90 || lat < -90){
      throw new IllegalArgumentException();
    }
    return lat;
  }

  private double parseLong(Map map){
    double lon = Double.parseDouble(map.get("longitude").toString());
    if(lon > 180 || lon < -180){
      throw new IllegalArgumentException();
    }
    return lon;
  }

  long getDistance() { return distance; }

  @Override
  public String toString() {
    return "TIPDistance{" +
            "origin=" + origin +
            ", destination=" + destination +
            ", earthRadius=" + earthRadius +
            ", distance=" + distance +
            ", requestVersion=" + requestVersion +
            ", requestType='" + requestType + '\'' +
            "}";
  }
}
