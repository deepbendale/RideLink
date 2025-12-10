package com.rideLink.app.RideLink.services.impl;

import com.rideLink.app.RideLink.dto.DriverDto;
import com.rideLink.app.RideLink.dto.RiderDto;
import com.rideLink.app.RideLink.entities.Driver;
import com.rideLink.app.RideLink.entities.Rating;
import com.rideLink.app.RideLink.entities.Ride;
import com.rideLink.app.RideLink.entities.Rider;
import com.rideLink.app.RideLink.exceptions.ResourceNotFoundException;
import com.rideLink.app.RideLink.exceptions.RuntimeConflictException;
import com.rideLink.app.RideLink.repositories.DriverRepository;
import com.rideLink.app.RideLink.repositories.RatingRepository;
import com.rideLink.app.RideLink.repositories.RiderRepository;
import com.rideLink.app.RideLink.services.EmailSenderService;
import com.rideLink.app.RideLink.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;
    private final EmailSenderService emailSenderService;

    @Override
    public DriverDto rateDriver(Ride ride, Integer rating) {

        Driver driver = ride.getDriver();

        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride " + ride.getId()));

        if (ratingObj.getDriverRating() != null)
            throw new RuntimeConflictException("Driver already rated");

        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);

        double newRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getDriverRating)
                .average()
                .orElse(0.0);

        driver.setRating(newRating);
        Driver saved = driverRepository.save(driver);

        // ⭐ EMAIL TO DRIVER
        emailSenderService.sendEmail(
                driver.getUser().getEmail(),
                "You Received a New Rating!",
                "Rider " + ride.getRider().getUser().getName() +
                        " rated you ⭐" + rating +
                        " for ride #" + ride.getId() +
                        "\nYour new average rating: ⭐" + newRating
        );

        return modelMapper.map(saved, DriverDto.class);
    }

    @Override
    public RiderDto rateRider(Ride ride, Integer rating) {

        Rider rider = ride.getRider();

        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride " + ride.getId()));

        if (ratingObj.getRiderRating() != null)
            throw new RuntimeConflictException("Rider already rated");

        ratingObj.setRiderRating(rating);
        ratingRepository.save(ratingObj);

        double newRating = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(Rating::getRiderRating)
                .average()
                .orElse(0.0);

        rider.setRating(newRating);
        Rider saved = riderRepository.save(rider);

        // ⭐ EMAIL TO RIDER
        emailSenderService.sendEmail(
                rider.getUser().getEmail(),
                "Driver Rated You",
                "Driver " + ride.getDriver().getUser().getName() +
                        " rated you ⭐" + rating +
                        " for ride #" + ride.getId() +
                        "\nYour new average rating: ⭐" + newRating
        );

        return modelMapper.map(saved, RiderDto.class);
    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating = Rating.builder()
                .driver(ride.getDriver())
                .rider(ride.getRider())
                .ride(ride)
                .build();
        ratingRepository.save(rating);
    }
}
