package com.piexie3.daraja_api_integration.controller;

import com.piexie3.daraja_api_integration.dto.BankResponse;
import com.piexie3.daraja_api_integration.dto.CreditDebitRequest;
import com.piexie3.daraja_api_integration.dto.EnquiryRequest;
import com.piexie3.daraja_api_integration.dto.TransferRequest;
import com.piexie3.daraja_api_integration.service.account.AccountServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/account")
@Tag(name = "User Account management Apis")
public class AccountController {
    private final AccountServiceImpl accountService;

    @GetMapping("/account_balance")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request){
        return accountService.balanceEnquiry(request);
    }

    @GetMapping("/account_name")
    public String nameEnquiry(@RequestBody EnquiryRequest request){
        return accountService.nameEnquiry(request);
    }

    @PostMapping("/deposit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request){
        return accountService.creditAccount(request);
    }

    @PostMapping("/withdraw")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request){
        return accountService.debitAccount(request);
    }

    @PostMapping("/transfer")
    public BankResponse transferMoney(@RequestBody TransferRequest request){
        return accountService.transfer(request);
    }

}
