package com.rideLink.app.RideLink.strategies.impl;

import com.rideLink.app.RideLink.dto.RideRequestDto;
import com.rideLink.app.RideLink.entities.Driver;
import com.rideLink.app.RideLink.entities.RideRequest;
import com.rideLink.app.RideLink.repositories.DriverRepository;
import com.rideLink.app.RideLink.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findTenNearestDrivers(rideRequest.getPickupLocation());
    }
}
