package com.rideLink.app.RideLink.strategies.impl;

import com.rideLink.app.RideLink.entities.Driver;
import com.rideLink.app.RideLink.entities.Payment;

import com.rideLink.app.RideLink.entities.enums.PaymentStatus;
import com.rideLink.app.RideLink.entities.enums.TransactionMethod;
import com.rideLink.app.RideLink.repositories.PaymentRepository;
import com.rideLink.app.RideLink.services.PaymentService;
import com.rideLink.app.RideLink.services.WalletService;
import com.rideLink.app.RideLink.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//Rider = 100
//Driver = 70 Deduct 30RS from driver's wallet


@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        double platformCommission = payment.getAmount()*PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(
                driver.getUser(),
                platformCommission,
                null,
                payment.getRide(),
                TransactionMethod.RIDE
        );


        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);

    }
}
