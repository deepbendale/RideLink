package com.rideLink.app.RideLink.strategies.impl;

import com.rideLink.app.RideLink.dto.RideRequestDto;
import com.rideLink.app.RideLink.entities.Driver;
import com.rideLink.app.RideLink.strategies.DriverMatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {
    @Override
    public List<Driver> findMatchingDriver(RideRequestDto rideRequestDto) {
        return List.of();
    }
}
