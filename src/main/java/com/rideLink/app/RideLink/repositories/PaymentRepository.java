package com.rideLink.app.RideLink.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rideLink.app.RideLink.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
}
