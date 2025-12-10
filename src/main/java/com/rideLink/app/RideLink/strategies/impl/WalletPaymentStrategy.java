package com.rideLink.app.RideLink.strategies.impl;

import com.rideLink.app.RideLink.entities.Driver;
import com.rideLink.app.RideLink.entities.Payment;
import com.rideLink.app.RideLink.entities.Rider;
import com.rideLink.app.RideLink.entities.enums.PaymentStatus;
import com.rideLink.app.RideLink.entities.enums.TransactionMethod;
import com.rideLink.app.RideLink.repositories.PaymentRepository;
import com.rideLink.app.RideLink.services.PaymentService;
import com.rideLink.app.RideLink.services.WalletService;
import com.rideLink.app.RideLink.strategies.PaymentStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(Payment payment) {
        // Defensive: if payment already confirmed do nothing
        if (payment.getPaymentStatus() == PaymentStatus.CONFIRMED) {
            return;
        }

        Driver driver = payment.getRide().getDriver();
        Rider rider  = payment.getRide().getRider();

        // 1) Deduct amount from rider (one call only)
        walletService.deductMoneyFromWallet(
                rider.getUser(),
                payment.getAmount(),
                null,
                payment.getRide(),
                TransactionMethod.RIDE
        );

        // 2) Add driver's cut
        double driversCut = payment.getAmount() * (1 - PLATFORM_COMMISSION);
        walletService.addMoneyToWallet(
                driver.getUser(),
                driversCut,
                null,
                payment.getRide(),
                TransactionMethod.RIDE
        );

        // 3) mark payment confirmed
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
