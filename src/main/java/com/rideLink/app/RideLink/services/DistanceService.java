package com.rideLink.app.RideLink.services;
import org.locationtech.jts.geom.Point;

public interface DistanceService {
    double calculateDistance(Point src, Point dest);


}
