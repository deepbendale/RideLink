package com.rideLink.app.RideLink.services.impl;

import com.rideLink.app.RideLink.entities.Payment;
import com.rideLink.app.RideLink.entities.Ride;
import com.rideLink.app.RideLink.entities.enums.PaymentStatus;
import com.rideLink.app.RideLink.exceptions.ResourceNotFoundException;
import com.rideLink.app.RideLink.repositories.PaymentRepository;
import com.rideLink.app.RideLink.services.EmailSenderService;
import com.rideLink.app.RideLink.services.PaymentService;
import com.rideLink.app.RideLink.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;
    private final EmailSenderService emailSenderService;

    @Override
    public void processPayment(Ride ride) {

        Payment payment = paymentRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for ride " + ride.getId()));

        // Idempotent: if already confirmed, do nothing
        if (payment.getPaymentStatus() == PaymentStatus.CONFIRMED) {
            return;
        }

        // Use payment strategy to handle wallet/cash split
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod())
                .processPayment(payment);

        // After strategy, ensure status is CONFIRMED
        if (payment.getPaymentStatus() != PaymentStatus.CONFIRMED) {
            payment.setPaymentStatus(PaymentStatus.CONFIRMED);
            paymentRepository.save(payment);
        }

        // notify rider
        try {
            emailSenderService.sendEmail(
                    ride.getRider().getUser().getEmail(),
                    "Ride Payment Successful",
                    "Your ride payment of ₹" + payment.getAmount() + " is successful."
            );
        } catch (Exception ignored) {
            // avoid failing the whole flow if email fails
        }
    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment p = Payment.builder()
                .ride(ride)
                .paymentMethod(ride.getPaymentMethod())
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        return paymentRepository.save(p);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus status) {
        payment.setPaymentStatus(status);
        paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentByRide(Ride ride) {
        return paymentRepository.findByRide(ride)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found for ride ID: " + ride.getId())
                );
    }
}
