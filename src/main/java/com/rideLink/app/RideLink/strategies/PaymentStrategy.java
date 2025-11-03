package com.rideLink.app.RideLink.strategies;

import com.rideLink.app.RideLink.entities.Payment;

public interface PaymentStrategy {

    void processPayment(Payment payment);
}
