package com.rideLink.app.RideLink.services;

import com.rideLink.app.RideLink.entities.Ride;
import com.rideLink.app.RideLink.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);

    Ride getRideById(Long rideId);
}
