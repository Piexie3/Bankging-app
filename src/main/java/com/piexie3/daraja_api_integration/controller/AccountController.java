package com.piexie3.daraja_api_integration.controller;

import com.piexie3.daraja_api_integration.dto.BankResponse;
import com.piexie3.daraja_api_integration.dto.CreditDebitRequest;
import com.piexie3.daraja_api_integration.dto.EnquiryRequest;
import com.piexie3.daraja_api_integration.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/account_balance")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request){
        return accountService.balanceEnquiry(request);
    }

    @GetMapping("/account_name")
    public String nameEnquiry(@RequestBody EnquiryRequest request){
        return accountService.nameEnquiry(request);
    }

    @PostMapping("/credit_account")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request){
        return accountService.creditAccount(request);
    }

}
