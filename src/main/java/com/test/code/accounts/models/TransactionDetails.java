package com.test.code.accounts.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
public class TransactionDetails {

    private BigDecimal balance;
    private String userName;
    private BigDecimal beforeBalance;
    private String transactionType;
}
