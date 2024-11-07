package com.piexie3.daraja_api_integration.controller;

import com.piexie3.daraja_api_integration.dto.BankResponse;
import com.piexie3.daraja_api_integration.dto.UserRequest;
import com.piexie3.daraja_api_integration.service.auth.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
@Tag(name = "User Authentication Apis")
public class UserController {
    private final UserServiceImpl userService;

    @Operation(
            summary = "creating new user account",
            description = "Using email and password to create user and assigning account number"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status created"
    )
    @PostMapping("/register")
    public BankResponse createAccount(@RequestBody UserRequest request){
        return userService.createAccount(request);
    }
}
