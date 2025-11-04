package com.rideLink.app.RideLink.strategies;

import com.rideLink.app.RideLink.entities.Payment;

public interface PaymentStrategy {
     Double PLATFORM_COMMISSION = 0.3;

    void processPayment(Payment payment);
}
