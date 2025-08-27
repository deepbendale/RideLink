package com.rideLink.app.RideLink.strategies;

import com.rideLink.app.RideLink.dto.RideRequestDto;
import com.rideLink.app.RideLink.entities.RideRequest;

public interface RideFareCalculationStrategy {

    double  RIDE_FARE_MULTIPLIER = 10;
    double calculateFare(RideRequest rideRequest);
}
