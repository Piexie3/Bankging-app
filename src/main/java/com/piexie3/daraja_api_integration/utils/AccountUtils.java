package com.piexie3.daraja_api_integration.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "User already exists";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account created successfully";
    public static final String ACCOUNT_CREATION_CODE = "002";

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