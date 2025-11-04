package com.rideLink.app.RideLink.dto;


import com.rideLink.app.RideLink.entities.enums.TransactionMethod;
import com.rideLink.app.RideLink.entities.enums.TransactionType;

import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@Builder
public class WalletTransactionDto {

    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;


    private RideDto ride;

    private String transactionId;


    private WalletDto wallet;


    private LocalDateTime timeStamp;
}
