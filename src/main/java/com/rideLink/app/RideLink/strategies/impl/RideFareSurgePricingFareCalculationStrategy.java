package com.rideLink.app.RideLink.strategies.impl;

import com.rideLink.app.RideLink.entities.RideRequest;
import com.rideLink.app.RideLink.services.DistanceService;
import com.rideLink.app.RideLink.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    private static final double BASE_FARE = 40.0;
    private static final double PER_KM_RATE = 12.0;
    private static final double SURGE_FACTOR = 2.0;


    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distanceKm = distanceService.calculateDistance(
                rideRequest.getPickupLocation(),
                rideRequest.getDropOffLocation()
        );

        double fare = (BASE_FARE + (distanceKm * PER_KM_RATE)) * SURGE_FACTOR;

        return Math.round(fare * 100.0) / 100.0;
    }
}
