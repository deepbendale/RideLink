package com.rideLink.app.RideLink.strategies.impl;

import com.rideLink.app.RideLink.entities.Driver;
import com.rideLink.app.RideLink.entities.RideRequest;
import com.rideLink.app.RideLink.repositories.DriverRepository;
import com.rideLink.app.RideLink.strategies.DriverMatchingStrategy;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional()
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findTenNearbyTopRatedDrivers(rideRequest.getPickupLocation());
    }
}
