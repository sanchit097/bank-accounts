package com.test.code.accounts;

import com.test.code.accounts.domains.Account;
import com.test.code.accounts.domains.Address;
import com.test.code.accounts.models.AccountDetails;
import com.test.code.accounts.models.TransactionDetails;
import com.test.code.accounts.models.UserAddress;
import com.test.code.accounts.models.UserDetails;

import java.math.BigDecimal;
import java.util.Random;

public class DataHelper {

    public static Random random = new Random();
    public static int upperbound = 1000;

    public static UserDetails getUserDetails(Integer ssn) {

        return UserDetails.builder()
                .ssn(ssn)
                .name("testUser")
                .emailId("testuser@gmail.com")
                .mobileNo(1243131)
                .address(UserAddress.builder().city("cph")
                        .country("DK")
                        .pinCode("2600")
                        .houseNo("12")
                        .streetName("street-1").build())
                .build();
    }

    public static AccountDetails getAccountDetails(Long accountNumber, BigDecimal amount) {
        return AccountDetails.builder()
                .accountNumber(accountNumber)
                .amount(amount)
                .build();
    }

    public static TransactionDetails getTransactionData(String type) {
        return TransactionDetails.builder()
                .transactionType(type)
                .balance(BigDecimal.valueOf(100))
                .build();
    }

    public static Account getAccountData(BigDecimal balance) {
        return Account.builder()
                .username("testUser")
                .accountId(1L)
                .balance(balance)
                .emailId("testuser@gmail.com")
                .ssn(123121)
                .address(getAddress()).build();
    }

    public static Address getAddress() {
        return Address.builder()
                .city("cph")
                .country("DK")
                .pinCode("2600")
                .houseNo("12")
                .streetName("street-1")
                .build();
    }
}
