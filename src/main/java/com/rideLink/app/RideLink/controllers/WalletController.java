package com.rideLink.app.RideLink.controllers;

import com.rideLink.app.RideLink.dto.WalletDto;
import com.rideLink.app.RideLink.dto.WalletTransactionDto;
import com.rideLink.app.RideLink.entities.User;
import com.rideLink.app.RideLink.entities.enums.TransactionMethod;
import com.rideLink.app.RideLink.services.UserService;
import com.rideLink.app.RideLink.services.WalletService;
import com.rideLink.app.RideLink.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final WalletTransactionService walletTransactionService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    // -------------------------------
    //   1️⃣ ADD MONEY TO WALLET
    // -------------------------------
    @PostMapping("/add")
    @PreAuthorize("hasRole('RIDER') or hasRole('DRIVER')")
    public ResponseEntity<WalletDto> addMoney(
            @RequestParam Double amount,
            @RequestParam(defaultValue = "BANKING") TransactionMethod method,
            Principal principal
    ) {
        User user = userService.getCurrentUser(principal);

        var wallet = walletService.addMoneyToWallet(
                user,
                amount,
                null,      // transactionId auto-created
                null,      // no ride involved
                method
        );

        return ResponseEntity.ok(modelMapper.map(wallet, WalletDto.class));
    }

    // ------------------------------------------------
    //   2️⃣ DEDUCT MONEY FROM WALLET (Optional)
    // ------------------------------------------------
    @PostMapping("/deduct")
    @PreAuthorize("hasRole('RIDER') or hasRole('DRIVER')")
    public ResponseEntity<WalletDto> deductMoney(
            @RequestParam Double amount,
            Principal principal
    ) {
        User user = userService.getCurrentUser(principal);

        var wallet = walletService.deductMoneyFromWallet(
                user,
                amount,
                null,
                null,
                TransactionMethod.BANKING
        );

        return ResponseEntity.ok(modelMapper.map(wallet, WalletDto.class));
    }

    // -----------------------------------
    //   3️⃣ GET MY WALLET DETAILS
    // -----------------------------------
    @GetMapping("/me")
    @PreAuthorize("hasRole('RIDER') or hasRole('DRIVER')")
    public ResponseEntity<WalletDto> getMyWallet(Principal principal) {
        User user = userService.getCurrentUser(principal);

        var wallet = walletService.findByUser(user);

        return ResponseEntity.ok(modelMapper.map(wallet, WalletDto.class));
    }

    // ------------------------------------------------------
    //   4️⃣ GET WALLET TRANSACTIONS (FULL HISTORY)
    // ------------------------------------------------------
    @GetMapping("/transactions")
    @PreAuthorize("hasRole('RIDER') or hasRole('DRIVER')")
    public ResponseEntity<?> getMyTransactions(Principal principal) {

        User user = userService.getCurrentUser(principal);

        var wallet = walletService.findByUser(user);

        var transactions = wallet.getTransactions().stream()
                .map(tx -> WalletTransactionDto.builder()
                        .id(tx.getId())
                        .amount(tx.getAmount())
                        .transactionId(tx.getTransactionId())
                        .transactionMethod(tx.getTransactionMethod())
                        .transactionType(tx.getTransactionType())
                        .rideId(tx.getRide() != null ? tx.getRide().getId() : null)
                        .timeStamp(tx.getTimeStamp())
                        .build()
                )
                .toList();


        return ResponseEntity.ok(transactions);
    }
}
