package com.rideLink.app.RideLink.dto;

import com.rideLink.app.RideLink.dto.WalletTransactionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {

    private Long id;
    private Double balance;

    // Optional: keep only transaction IDs or a light DTO
    private List<WalletTransactionDto> transactions;
}
