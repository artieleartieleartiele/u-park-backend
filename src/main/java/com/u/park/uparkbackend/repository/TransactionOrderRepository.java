package com.u.park.uparkbackend.repository;

import com.u.park.uparkbackend.model.TransactionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionOrderRepository extends JpaRepository<TransactionOrder, Long> {

    @Query("SELECT MAX(transaction) FROM TransactionOrder transaction WHERE transaction.checkOut IS NULL AND transaction.userId = ?1")
    TransactionOrder findOneActiveTransactionByUserId(Long userId);

    List<TransactionOrder> findByUserId(Long userId);
}
