package com.rideLink.app.RideLink.services;

import com.rideLink.app.RideLink.dto.DriverDto;
import com.rideLink.app.RideLink.dto.RideDto;
import com.rideLink.app.RideLink.dto.RideRequestDto;
import com.rideLink.app.RideLink.dto.RiderDto;
import com.rideLink.app.RideLink.entities.Rider;
import com.rideLink.app.RideLink.entities.User;

import java.util.List;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    DriverDto rateDriver(Long rideId, Integer rating);

    RiderDto getMyProfile();

    List<RideDto> getAllMyRides();

    Rider createNewRider(User user);

    Rider getCurrentRider();
}
