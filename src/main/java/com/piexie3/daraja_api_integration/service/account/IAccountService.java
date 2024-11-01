package com.piexie3.daraja_api_integration.service.account;

import com.piexie3.daraja_api_integration.dto.BankResponse;
import com.piexie3.daraja_api_integration.dto.CreditDebitRequest;
import com.piexie3.daraja_api_integration.dto.EnquiryRequest;

public interface IAccountService {
    BankResponse balanceEnquiry(EnquiryRequest request);
    String nameEnquiry(EnquiryRequest request);
    BankResponse creditAccount(CreditDebitRequest request);
}