package com.rideLink.app.RideLink.services.impl;

import com.rideLink.app.RideLink.dto.DriverDto;
import com.rideLink.app.RideLink.dto.RideDto;
import com.rideLink.app.RideLink.dto.RiderDto;
import com.rideLink.app.RideLink.entities.*;
import com.rideLink.app.RideLink.entities.enums.PaymentStatus;
import com.rideLink.app.RideLink.entities.enums.RideRequestStatus;
import com.rideLink.app.RideLink.entities.enums.RideStatus;
import com.rideLink.app.RideLink.exceptions.ResourceNotFoundException;
import com.rideLink.app.RideLink.repositories.DriverRepository;
import com.rideLink.app.RideLink.services.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
    private final RatingService ratingService;
    private final EmailSenderService emailSenderService;

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {

        RideRequest req = rideRequestService.findRideRequestById(rideRequestId);

        if (!req.getRideRequestStatus().equals(RideRequestStatus.PENDING)) {
            throw new RuntimeException("Ride request not pending.");
        }

        Driver driver = getCurrentDriver();
        if (!driver.getAvailable()) {
            throw new RuntimeException("Driver unavailable.");
        }

        updateDriverAvailiability(driver, false);

        Ride ride = rideService.createNewRide(req, driver);

        // email rider
        emailSenderService.sendEmail(
                ride.getRider().getUser().getEmail(),
                "Ride Accepted",
                "Driver " + driver.getUser().getName() + " has accepted your ride."
        );

        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot cancel this ride.");
        }

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Only confirmed rides can be cancelled.");
        }

        rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        updateDriverAvailiability(driver, true);

        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    public RideDto startRide(Long rideId, String otp) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver not allowed.");
        }

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride must be CONFIRMED to start.");
        }

        if (!otp.equals(ride.getOtp())) {
            throw new RuntimeException("OTP incorrect.");
        }

        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);

        paymentService.createNewPayment(savedRide);
        ratingService.createNewRating(savedRide);

        // notify rider
        emailSenderService.sendEmail(
                ride.getRider().getUser().getEmail(),
                "Ride Started",
                "Your ride with driver " + driver.getUser().getName() + " has started."
        );

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    @Transactional
    public RideDto endRide(Long rideId) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver not allowed.");
        }

        // ⭐ Ride must be ONGOING
        if (!ride.getRideStatus().equals(RideStatus.ONGOING)) {
            throw new RuntimeException("Ride is not ONGOING. Current: " + ride.getRideStatus());
        }

        // get payment and check its status (prevent double payment)
        Payment payment = paymentService.getPaymentByRide(ride);
        boolean alreadyPaid = payment.getPaymentStatus() == PaymentStatus.CONFIRMED;

        // mark end time and status
        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);

        // set driver available
        updateDriverAvailiability(driver, true);

        // charge only if not already charged
        if (!alreadyPaid) {
            paymentService.processPayment(ride);
        }

        // Rider email
        emailSenderService.sendEmail(
                ride.getRider().getUser().getEmail(),
                "Ride Completed",
                "Your ride has ended. Fare: ₹" + ride.getFare()
        );

        // Driver email
        emailSenderService.sendEmail(
                ride.getDriver().getUser().getEmail(),
                "Ride Complete",
                "You successfully completed ride #" + ride.getId()
        );

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    @Transactional
    public RiderDto rateRider(Long rideId, Integer rating) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver not allowed.");
        }

        if (!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException("Ride not completed yet.");
        }

        RiderDto dto = ratingService.rateRider(ride, rating);

        emailSenderService.sendEmail(
                driver.getUser().getEmail(),
                "Rating Submitted",
                "You rated rider " + ride.getRider().getUser().getName() +
                        " ⭐ " + rating + " for ride #" + rideId
        );

        return dto;
    }

    @Override
    public DriverDto getMyProfile() {
        return modelMapper.map(getCurrentDriver(), DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver d = getCurrentDriver();
        return rideService.getAllRidesOfDriver(d, pageRequest)
                .map(r -> modelMapper.map(r, RideDto.class));
    }

    @Override
    public Driver getCurrentDriver() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        return driverRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found for user id " + user.getId())
                );
    }

    @Override
    public Driver updateDriverAvailiability(Driver driver, boolean available) {
        driver.setAvailable(available);
        return driverRepository.save(driver);
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return driverRepository.save(driver);
    }
}
