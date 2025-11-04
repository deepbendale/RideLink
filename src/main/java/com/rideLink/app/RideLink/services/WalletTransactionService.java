package com.rideLink.app.RideLink.services;


import com.rideLink.app.RideLink.dto.WalletTransactionDto;
import com.rideLink.app.RideLink.entities.WalletTransaction;

public interface WalletTransactionService {

    void createNewWalletTransaction(WalletTransaction walletTransaction);
}
