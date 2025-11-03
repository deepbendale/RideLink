package com.rideLink.app.RideLink.services;
import com.rideLink.app.RideLink.entities.Ride;
import com.rideLink.app.RideLink.entities.Payment;

public interface PaymentService {
    void processPayment(Payment payment);

    Payment createNewPayment(Ride ride);
}
