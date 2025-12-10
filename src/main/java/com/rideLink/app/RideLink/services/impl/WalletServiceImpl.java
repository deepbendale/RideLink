package com.rideLink.app.RideLink.services.impl;

import com.rideLink.app.RideLink.entities.Ride;
import com.rideLink.app.RideLink.entities.User;
import com.rideLink.app.RideLink.entities.Wallet;
import com.rideLink.app.RideLink.entities.WalletTransaction;
import com.rideLink.app.RideLink.entities.enums.TransactionMethod;
import com.rideLink.app.RideLink.entities.enums.TransactionType;
import com.rideLink.app.RideLink.exceptions.ResourceNotFoundException;
import com.rideLink.app.RideLink.repositories.WalletRepository;
import com.rideLink.app.RideLink.services.EmailSenderService;
import com.rideLink.app.RideLink.services.WalletService;
import com.rideLink.app.RideLink.services.WalletTransactionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;
    private final ModelMapper modelMapper;
    private final EmailSenderService emailSenderService;

    private String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis();
    }

    @Override
    @Transactional
    public Wallet addMoneyToWallet(
            User user,
            Double amount,
            String transactionId,
            Ride ride,
            TransactionMethod method
    ) {

        Wallet wallet = findByUser(user);

        if (transactionId == null)
            transactionId = generateTransactionId();

        wallet.setBalance(wallet.getBalance() + amount);

        WalletTransaction tx = WalletTransaction.builder()
                .transactionId(transactionId)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(method)
                .amount(amount)
                .wallet(wallet)
                .ride(ride)
                .build();

        walletTransactionService.createNewWalletTransaction(tx);
        walletRepository.save(wallet);

        // Email notification
        emailSenderService.sendEmail(
                user.getEmail(),
                "₹" + amount + " Added to Wallet",
                "Your wallet has been credited with ₹" + amount + ".\nTransaction ID: " + transactionId
        );

        return wallet;
    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(
            User user,
            Double amount,
            String transactionId,
            Ride ride,
            TransactionMethod method
    ) {

        Wallet wallet = findByUser(user);

        if (wallet.getBalance() < amount)
            throw new RuntimeException("Insufficient wallet balance");

        if (transactionId == null)
            transactionId = generateTransactionId();

        wallet.setBalance(wallet.getBalance() - amount);

        WalletTransaction tx = WalletTransaction.builder()
                .transactionId(transactionId)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(method)
                .amount(amount)
                .wallet(wallet)
                .ride(ride)
                .build();

        walletTransactionService.createNewWalletTransaction(tx);
        walletRepository.save(wallet);

        // Email notification
        emailSenderService.sendEmail(
                user.getEmail(),
                "₹" + amount + " Deducted from Wallet",
                "Your wallet has been debited by ₹" + amount + ".\nTransaction ID: " + transactionId
        );

        return wallet;
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(0.0);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Wallet not found for user id: " + user.getId())
                );
    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Wallet Not found with id: " + walletId)
                );
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {
        // TODO: implement if required
    }
}
