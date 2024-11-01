package com.piexie3.daraja_api_integration.service.account;

import com.piexie3.daraja_api_integration.dto.AccountInfo;
import com.piexie3.daraja_api_integration.dto.BankResponse;
import com.piexie3.daraja_api_integration.dto.CreditDebitRequest;
import com.piexie3.daraja_api_integration.dto.EnquiryRequest;
import com.piexie3.daraja_api_integration.models.User;
import com.piexie3.daraja_api_integration.repository.auth.UserRepository;
import com.piexie3.daraja_api_integration.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService{
    private final UserRepository userRepository;
    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        boolean existsByAccountNumber = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!existsByAccountNumber){
            return BankResponse.builder()
                    .code(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .message(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .data(null)
                    .build();
        }
        User user = userRepository.findByAccountNumber(request.getAccountNumber());
        return BankResponse.builder()
                .code(AccountUtils.ACCOUNT_FOUND_CODE)
                .message(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .data(AccountInfo.builder()
                        .accountBalance(user.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .accountName(user.getFirstName()+" "+user.getLastName()+" "+user.getSurname())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {
        boolean existsByAccountNumber = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!existsByAccountNumber){
            return AccountUtils.ACCOUNT_EXISTS_MESSAGE;
        }
        User user = userRepository.findByAccountNumber(request.getAccountNumber());
        return user.getFirstName()+" "+user.getLastName()+" "+user.getSurname();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
        boolean existsByAccountNumber = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!existsByAccountNumber){
            return BankResponse.builder()
                    .code(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .message(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .data(null)
                    .build();
        }

        User user = userRepository.findByAccountNumber(request.getAccountNumber());
        // to add big decimal is by getting first the current BigDecimal and adding it to another
        user.setAccountBalance(user.getAccountBalance().add(request.getAmount()));
        userRepository.save(user);
        return BankResponse.builder()
                .code(AccountUtils.ACCOUNT_CREDITED_CODE)
                .message(AccountUtils.ACCOUNT_CREDITED_MESSAGE)
                .data(AccountInfo.builder()
                        .accountBalance(user.getAccountBalance())
                        .accountName(user.getFirstName()+" "+user.getLastName()+" "+user.getSurname())
                        .accountNumber(request.getAccountNumber())
                        .build())
                .build();
    }
}
