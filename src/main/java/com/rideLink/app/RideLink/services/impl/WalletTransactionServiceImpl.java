package com.rideLink.app.RideLink.services.impl;

import com.rideLink.app.RideLink.dto.WalletTransactionDto;
import com.rideLink.app.RideLink.entities.WalletTransaction;
import com.rideLink.app.RideLink.repositories.WalletTransactionRepository;
import com.rideLink.app.RideLink.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {
    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {

        walletTransactionRepository.save(walletTransaction);
    }

}
