package com.piexie3.daraja_api_integration.service.transaction;

import com.piexie3.daraja_api_integration.dto.TransactionDto;

public interface TransactionService {
    void saveTransaction(TransactionDto transaction);
}
