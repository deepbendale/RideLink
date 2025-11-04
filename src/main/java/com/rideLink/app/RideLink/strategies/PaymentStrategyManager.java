package com.rideLink.app.RideLink.strategies;


import com.rideLink.app.RideLink.entities.enums.PaymentMethod;
import com.rideLink.app.RideLink.strategies.impl.CashPaymentStrategy;
import com.rideLink.app.RideLink.strategies.impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final WalletPaymentStrategy walletPaymentStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
        return switch (paymentMethod){
            case WALLET ->  walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
            default -> throw new RuntimeException("Invalid Payment Method");
        };
    }

}
