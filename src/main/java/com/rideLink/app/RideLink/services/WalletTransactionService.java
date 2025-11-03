package com.rideLink.app.RideLink.services;


import com.rideLink.app.RideLink.dto.WalletTransactionDto;

public interface WalletTransactionService {

    void createNewWalletTransaction(WalletTransactionDto walletTransactionDto);
}
