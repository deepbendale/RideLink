package com.rideLink.app.RideLink.services.impl;

import com.rideLink.app.RideLink.entities.WalletTransaction;
import com.rideLink.app.RideLink.repositories.WalletTransactionRepository;
import com.rideLink.app.RideLink.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {
    private final WalletTransactionRepository repo;

    @Override
    public void createNewWalletTransaction(WalletTransaction tx) {
        repo.save(tx);
    }
}
