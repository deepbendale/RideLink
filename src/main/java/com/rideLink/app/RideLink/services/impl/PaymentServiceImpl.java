package com.rideLink.app.RideLink.services.impl;

import com.rideLink.app.RideLink.entities.Payment;
import com.rideLink.app.RideLink.entities.Ride;
import com.rideLink.app.RideLink.repositories.PaymentRepository;
import com.rideLink.app.RideLink.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {

    }

    @Override
    public Payment createNewPayment(Ride ride) {
        return null;
    }
}
