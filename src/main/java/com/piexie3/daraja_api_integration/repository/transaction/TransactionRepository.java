package com.piexie3.daraja_api_integration.repository.transaction;

import com.piexie3.daraja_api_integration.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,String> {
}
