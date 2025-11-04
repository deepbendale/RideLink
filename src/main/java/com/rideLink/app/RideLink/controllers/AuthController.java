package com.rideLink.app.RideLink.controllers;

import com.rideLink.app.RideLink.dto.DriverDto;
import com.rideLink.app.RideLink.dto.OnBoardDriverDto;
import com.rideLink.app.RideLink.dto.SignupDto;
import com.rideLink.app.RideLink.dto.UserDto;
import com.rideLink.app.RideLink.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    ResponseEntity<UserDto> signUp(@RequestBody SignupDto signupDto) {
        return new ResponseEntity<>(authService.signup(signupDto), HttpStatus.CREATED);
    }

    @PostMapping("/onBoardNewDriver/{userId}")
    ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId, @RequestBody OnBoardDriverDto onBoardDriverDto){
        return new ResponseEntity<>(authService.onboardNewDriver(userId, onBoardDriverDto.getVehicleId()), HttpStatus.CREATED);
    }

}