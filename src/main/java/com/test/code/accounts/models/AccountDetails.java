package com.test.code.accounts.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class AccountDetails {


    @NotNull
    private Long accountNumber;
    private String username;
    private String emailId;

    @NotNull()
    private BigDecimal amount;


}
