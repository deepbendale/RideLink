package com.rideLink.app.RideLink.services.impl;

import org.springframework.stereotype.Service;

import com.rideLink.app.RideLink.dto.WalletTransactionDto;
import com.rideLink.app.RideLink.repositories.WalletTransactionRepository;
import com.rideLink.app.RideLink.services.WalletTransactionService;
import com.rideLink.app.RideLink.entities.WalletTransaction;
import org.modelmapper.ModelMapper;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService{

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransactionDto walletTransaction) {
       
    }
    
}
