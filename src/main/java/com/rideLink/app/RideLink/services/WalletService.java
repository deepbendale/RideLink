package com.rideLink.app.RideLink.services;

import com.rideLink.app.RideLink.entities.Ride;
import com.rideLink.app.RideLink.entities.User;
import com.rideLink.app.RideLink.entities.Wallet;
import com.rideLink.app.RideLink.entities.enums.TransactionMethod;

public interface WalletService {
    Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    void withdrawAllMyMoneyFromWallet();

    Wallet findWalletById(Long walletId);

    Wallet createNewWallet(User user);

    Wallet findByUser(User user);
}
