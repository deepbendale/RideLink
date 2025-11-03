package com.rideLink.app.RideLink.services;

import com.rideLink.app.RideLink.dto.RideRequestDto;
import com.rideLink.app.RideLink.entities.Driver;
import com.rideLink.app.RideLink.entities.Ride;
import com.rideLink.app.RideLink.entities.RideRequest;
import com.rideLink.app.RideLink.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {

    Ride getRideById(Long rideId);

    void matchWithDrivers(RideRequestDto rideRequestDto);

    Ride createNewRide(RideRequest rideRequest, Driver driver);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Long riderId, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Long driverId, PageRequest pageRequest);
}