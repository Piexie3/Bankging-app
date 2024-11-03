package com.piexie3.daraja_api_integration.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfo {
    @Schema(
            name = "User account name"
    )
    private String accountName;
    @Schema(
            name = "User account balance"
    )
    private BigDecimal accountBalance;
    @Schema(
            name = "User account number"
    )
    private String accountNumber;
}
