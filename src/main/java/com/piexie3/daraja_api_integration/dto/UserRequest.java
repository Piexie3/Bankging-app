package com.piexie3.daraja_api_integration.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String surname;
    private String gender;
    private String address;
    private String stateOfOrigin;
    private String email;
    private String password;
    private String phoneNumber;
    private String altPhoneNumber;
}
