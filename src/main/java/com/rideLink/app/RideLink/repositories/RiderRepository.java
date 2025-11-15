package com.rideLink.app.RideLink.repositories;

import com.rideLink.app.RideLink.entities.Rider;
import com.rideLink.app.RideLink.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByUser(User user);
}
