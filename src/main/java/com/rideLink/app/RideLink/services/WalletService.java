package com.rideLink.app.RideLink.services;

import com.rideLink.app.RideLink.entities.User;
import com.rideLink.app.RideLink.entities.Wallet;

public interface WalletService {
    Wallet addMoneyToWallet(User user, Double amount);

    void withdrawAllMyMoneyFromWallet();

    Wallet findWalletById(Long walletId);

    Wallet createNewWallet(User user);

    Wallet findByUser(User user);
}
