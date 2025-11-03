package com.rideLink.app.RideLink.repositories;

import com.rideLink.app.RideLink.entities.Wallet;
import com.rideLink.app.RideLink.entities.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
}
