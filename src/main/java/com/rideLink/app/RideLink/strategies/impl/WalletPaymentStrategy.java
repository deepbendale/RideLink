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

//Rider -> 232 rs Driver = 500
//Ride cost = 100, commission = 30
//Rider -> 232-100 = 132
//Driver = 500+(100-30) = 570

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Rider rider  = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(), null, payment.getRide(), TransactionMethod.RIDE);
        double driversCut = payment.getAmount() * (1-PLATFORM_COMMISSION);


        walletService.addMoneyToWallet(driver.getUser(), driversCut,null, payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);


    }


}
