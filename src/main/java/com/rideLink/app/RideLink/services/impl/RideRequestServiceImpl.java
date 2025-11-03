package com.rideLink.app.RideLink.services.impl;

import org.springframework.stereotype.Service;

import com.rideLink.app.RideLink.entities.RideRequest;
import com.rideLink.app.RideLink.exceptions.ResourceNotFoundException;
import com.rideLink.app.RideLink.repositories.RideRequestRepository;
import com.rideLink.app.RideLink.services.RideRequestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;

    @Override
    public RideRequest findRideRequestById(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with id: "+rideRequestId));
    }

    @Override
    public void update(RideRequest rideRequest) {
        rideRequestRepository.findById(rideRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with id: "+rideRequest.getId()));
        rideRequestRepository.save(rideRequest);
    }
}
