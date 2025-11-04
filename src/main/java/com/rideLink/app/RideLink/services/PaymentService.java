package com.rideLink.app.RideLink.services;

import com.rideLink.app.RideLink.entities.Payment;
import com.rideLink.app.RideLink.entities.Ride;
import com.rideLink.app.RideLink.entities.enums.PaymentStatus;

public interface PaymentService {

    void processPayment(Ride ride);
    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus status);
}
