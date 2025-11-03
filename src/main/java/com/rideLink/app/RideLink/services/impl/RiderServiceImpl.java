package com.rideLink.app.RideLink.services.impl;

import com.rideLink.app.RideLink.dto.DriverDto;
import com.rideLink.app.RideLink.dto.RideDto;
import com.rideLink.app.RideLink.dto.RideRequestDto;
import com.rideLink.app.RideLink.dto.RiderDto;
import com.rideLink.app.RideLink.entities.RideRequest;
import com.rideLink.app.RideLink.entities.Rider;
import com.rideLink.app.RideLink.entities.User;
import com.rideLink.app.RideLink.entities.enums.RideRequestStatus;
import com.rideLink.app.RideLink.entities.enums.RideStatus;
import com.rideLink.app.RideLink.exceptions.ResourceNotFoundException;
import com.rideLink.app.RideLink.repositories.RideRequestRepository;
import com.rideLink.app.RideLink.repositories.RiderRepository;
import com.rideLink.app.RideLink.entities.Driver;
import com.rideLink.app.RideLink.entities.Ride;
import com.rideLink.app.RideLink.services.DriverService;
import com.rideLink.app.RideLink.services.RideService;
import com.rideLink.app.RideLink.services.RiderService;
import com.rideLink.app.RideLink.strategies.RideStrategyManager;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final RideService rideService;
    private final DriverService driverService;

    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        List<Driver> drivers = rideStrategyManager
                .driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);


//        TODO : Send notification to all the drivers about this ride request

        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);
        if(rider.equals(ride.getRider())){
            throw new RuntimeException(("Rider doe not own this ride with id: "+rideId));
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ride.getRideStatus());
        }

        Ride saveRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        driverService.updateDriverAvailiability(ride.getDriver(), true);

        return modelMapper.map(saveRide, RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        Rider currentRider = getCurrentRider();
        return modelMapper.map(currentRider, RiderDto.class); 

    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider, pageRequest).map(
            ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
//        TODO : implement Spring security
        return riderRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException(
                "Rider not found with id: "+1
        ));
    }

}
