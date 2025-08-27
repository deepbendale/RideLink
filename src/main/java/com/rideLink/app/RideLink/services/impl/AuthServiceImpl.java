package com.rideLink.app.RideLink.services.impl;

import com.rideLink.app.RideLink.dto.DriverDto;
import com.rideLink.app.RideLink.dto.SignupDto;
import com.rideLink.app.RideLink.dto.UserDto;
import com.rideLink.app.RideLink.entities.Rider;
import com.rideLink.app.RideLink.entities.User;
import com.rideLink.app.RideLink.entities.enums.Role;
import com.rideLink.app.RideLink.exceptions.RuntimeConflictException;
import com.rideLink.app.RideLink.repositories.UserRepository;
import com.rideLink.app.RideLink.services.AuthService;
import com.rideLink.app.RideLink.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RiderService riderService;

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public UserDto signup(SignupDto signupDto) {

        User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);
        if(user!=null)
            throw new RuntimeConflictException("Cannot signup, User already exists with email "+signupDto.getEmail());;

        User mappedUser = modelMapper.map(signupDto, User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepository.save(mappedUser);

        //create user related entities

        Rider rider = riderService.createNewRider(savedUser);

        //TODO add wallet related service here

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId) {
        return null;
    }
}
