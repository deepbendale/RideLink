package com.rideLink.app.RideLink.services;

import com.rideLink.app.RideLink.dto.DriverDto;
import com.rideLink.app.RideLink.dto.RiderDto;
import com.rideLink.app.RideLink.entities.Driver;
import com.rideLink.app.RideLink.entities.Ride;
import com.rideLink.app.RideLink.entities.Rider;

public interface RatingService {

    DriverDto rateDriver(Ride ride, Integer rating);
    RiderDto rateRider(Ride  ride, Integer rating);
    void createNewRating(Ride ride);
}
