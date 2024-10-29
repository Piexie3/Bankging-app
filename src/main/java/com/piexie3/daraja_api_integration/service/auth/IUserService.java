package com.piexie3.daraja_api_integration.service.auth;

import com.piexie3.daraja_api_integration.dto.BankResponse;
import com.piexie3.daraja_api_integration.dto.UserRequest;

public interface IUserService {
    BankResponse createAccount(UserRequest request);
}
