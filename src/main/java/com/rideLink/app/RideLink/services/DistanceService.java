package com.rideLink.app.RideLink.services;

import com.rideLink.app.RideLink.dto.PointDto;
import org.locationtech.jts.geom.Point;

public interface DistanceService {
    double calculateDistance(Point src, Point dest);


}
