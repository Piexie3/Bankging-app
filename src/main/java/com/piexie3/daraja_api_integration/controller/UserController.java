package com.piexie3.daraja_api_integration.controller;

import com.piexie3.daraja_api_integration.dto.BankResponse;
import com.piexie3.daraja_api_integration.dto.UserRequest;
import com.piexie3.daraja_api_integration.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public BankResponse createAccount(@RequestBody UserRequest request){
        return userService.createAccount(request);
    }
}
