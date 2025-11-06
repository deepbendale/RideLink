package com.rideLink.app.RideLink.repositories;

import com.rideLink.app.RideLink.entities.Driver;
import com.rideLink.app.RideLink.entities.Rating;
import com.rideLink.app.RideLink.entities.Ride;
import com.rideLink.app.RideLink.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByRider(Rider rider);
    List<Rating> findByDriver(Driver driver);

    Optional<Rating> findByRide(Ride ride);
}
