package com.rideLink.app.RideLink.services;
import com.rideLink.app.RideLink.entities.Wallet;
import com.rideLink.app.RideLink.entities.User;

public interface WalletService {
    
    Wallet addMoneyToWallet(Long userId, Double amount);

    void withdrawAllMyMoneyFromWallet();

    Wallet findWalletById(Long walletId);

    Wallet createNewWallet(User user);
}
