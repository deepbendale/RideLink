package com.rideLink.app.RideLink.strategies;

import com.rideLink.app.RideLink.dto.RideRequestDto;

public interface RideFareCalculationStrategy {
    double calculateFare(RideRequestDto rideRequestDto);
}
