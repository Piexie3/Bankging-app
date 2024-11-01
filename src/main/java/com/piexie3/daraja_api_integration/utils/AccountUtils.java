package com.piexie3.daraja_api_integration.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "User already exists";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account created successfully";
    public static final String ACCOUNT_CREATION_CODE = "002";
    public static final String ACCOUNT_NOT_EXISTS_CODE = "003";
    public static final String ACCOUNT_NOT_EXISTS_MESSAGE = "User with the provided account number does not exists";
    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_FOUND_MESSAGE = "User account found";
    public static final String ACCOUNT_CREDITED_CODE = "005";
    public static final String ACCOUNT_CREDITED_MESSAGE = "User account credited successfully";
    public static final String ACCOUNT_DEBITED_CODE = "006";
    public static final String ACCOUNT_DEBITED_MESSAGE = "User account debited successfully";
    public static final String INSUFFICIENT_FUND_CODE = "007";
    public static final String INSUFFICIENT_FUND_MESSAGE = "Insufficient balance";


    public static  String generateAccountNumber(){
        Year currentYear = Year.now();
        int minSixDig = 100000;
        int maxSixDig = 999999;
        int randNumber = (int)(Math.floor(Math.random()*(maxSixDig-minSixDig+1)+minSixDig));
        System.out.println("........... Random generated number "+ randNumber + "................");
        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);
        StringBuilder accountNumber = new StringBuilder();
       return accountNumber.append(year).append(randomNumber).toString();
    }
}
