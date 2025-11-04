package com.rideLink.app.RideLink.services;

import com.rideLink.app.RideLink.dto.DriverDto;
import com.rideLink.app.RideLink.dto.SignupDto;
import com.rideLink.app.RideLink.dto.UserDto;

public interface AuthService {

    String login(String email, String password);

    UserDto signup(SignupDto signupDTO);

    DriverDto onboardNewDriver(Long userId,String vehicleId);
}
