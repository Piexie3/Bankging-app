package com.piexie3.daraja_api_integration.service.auth;

import com.piexie3.daraja_api_integration.dto.AccountInfo;
import com.piexie3.daraja_api_integration.dto.BankResponse;
import com.piexie3.daraja_api_integration.dto.UserRequest;
import com.piexie3.daraja_api_integration.models.User;
import com.piexie3.daraja_api_integration.repository.auth.UserRepository;
import com.piexie3.daraja_api_integration.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    @Override
    public BankResponse createAccount(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())){
            return BankResponse.builder()
                    .code(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .message(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .data(null)
                    .build();
        }else {
            User user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .surname(request.getSurname())
                    .gender(request.getGender())
                    .address(request.getAddress())
                    .stateOfOrigin(request.getStateOfOrigin())
                    .accountNumber(AccountUtils.generateAccountNumber())
                    .accountBalance(BigDecimal.ZERO)
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .altPhoneNumber(request.getAltPhoneNumber())
                    .status("ACTIVE")
                    .build();

           User saved= userRepository.save(user);
           return BankResponse.builder()
                   .code(AccountUtils.ACCOUNT_CREATION_CODE)
                   .message(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                   .data(AccountInfo.builder()
                           .accountBalance(saved.getAccountBalance())
                           .accountName(saved.getFirstName()+" "+saved.getLastName()+" "+saved.getSurname())
                           .accountNumber(saved.getAccountNumber())
                           .build())
                   .build();
        }

    }
}