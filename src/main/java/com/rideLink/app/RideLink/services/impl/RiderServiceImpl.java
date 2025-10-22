package com.rideLink.app.RideLink.services.impl;

import com.rideLink.app.RideLink.dto.DriverDto;
import com.rideLink.app.RideLink.dto.RideDto;
import com.rideLink.app.RideLink.dto.RideRequestDto;
import com.rideLink.app.RideLink.dto.RiderDto;
import com.rideLink.app.RideLink.entities.RideRequest;
import com.rideLink.app.RideLink.entities.Rider;
import com.rideLink.app.RideLink.entities.User;
import com.rideLink.app.RideLink.entities.enums.RideRequestStatus;
import com.rideLink.app.RideLink.exceptions.ResourceNotFoundException;
import com.rideLink.app.RideLink.repositories.RideRequestRepository;
import com.rideLink.app.RideLink.repositories.RiderRepository;
import com.rideLink.app.RideLink.services.RiderService;
import com.rideLink.app.RideLink.strategies.DriverMatchingStrategy;
import com.rideLink.app.RideLink.strategies.RideFareCalculationStrategy;
import com.rideLink.app.RideLink.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider = getCurrentRider();

        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

//        log.info(rideRequest.toString());

        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);

        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider.builder()
                .user(user)
                .rating(0.0)
                .build();

        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
        //TODO : implement spring security
        return  riderRepository.findById(1L).orElseThrow(()-> new ResourceNotFoundException(
                "Rider not found with id: "+1
        ));
    }
}
