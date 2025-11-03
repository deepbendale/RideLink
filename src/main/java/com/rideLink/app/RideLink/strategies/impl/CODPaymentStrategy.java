package com.rideLink.app.RideLink.strategies.impl;

import com.rideLink.app.RideLink.entities.Driver;
import com.rideLink.app.RideLink.entities.Payment;
import com.rideLink.app.RideLink.entities.Wallet;
import com.rideLink.app.RideLink.services.WalletService;
import com.rideLink.app.RideLink.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//Rider = 100
//Driver = 70 Deduct 30RS from driver's wallet


@Service
@RequiredArgsConstructor
public class CODPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;

    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Wallet driverWallet = walletService.findByUser(driver.getUser());

    }
}
