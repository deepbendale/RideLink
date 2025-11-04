package com.rideLink.app.RideLink.services;

import com.rideLink.app.RideLink.dto.DriverDto;
import com.rideLink.app.RideLink.dto.RideDto;
import com.rideLink.app.RideLink.dto.RiderDto;
import com.rideLink.app.RideLink.entities.Driver;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DriverService {

    RideDto acceptRide(Long rideRequestId);

    RideDto cancelRide(Long rideId);

    RideDto startRide(Long rideId, String otp);

    RideDto endRide(Long rideId);

    RiderDto rateRider(Long rideId, Integer rating);

    DriverDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Driver getCurrentDriver();

    Driver updateDriverAvailiability(Driver driver, boolean available);

    Driver createNewDriver(Driver driver);
}