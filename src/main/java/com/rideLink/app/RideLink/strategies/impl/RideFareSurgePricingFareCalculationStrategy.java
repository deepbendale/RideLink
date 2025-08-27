package com.rideLink.app.RideLink.strategies.impl;

import com.rideLink.app.RideLink.dto.RideRequestDto;
import com.rideLink.app.RideLink.entities.RideRequest;
import com.rideLink.app.RideLink.strategies.RideFareCalculationStrategy;

public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {
    @Override
    public double calculateFare(RideRequest rideRequest) {
        return 0;
    }
}
