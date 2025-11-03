package com.rideLink.app.RideLink.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rideLink.app.RideLink.entities.Wallet;

@Repository
public interface WalletTransactionRepository extends JpaRepository<Wallet, Long> {
    
}
