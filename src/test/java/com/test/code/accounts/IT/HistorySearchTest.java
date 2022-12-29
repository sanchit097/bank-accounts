package com.test.code.accounts.IT;

import com.test.code.accounts.models.TransactionDetails;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

import static com.test.code.accounts.DataHelper.getAccountDetails;
import static com.test.code.accounts.DataHelper.getUserDetails;
import static com.test.code.accounts.HelperMethod.getBalanceCheckResponse;
import static com.test.code.accounts.HelperMethod.getCreateAccountResponse;
import static com.test.code.accounts.HelperMethod.getDepositResponse;
import static com.test.code.accounts.HelperMethod.getTransactionHistoryResponse;
import static com.test.code.accounts.HelperMethod.getWithdrawalResponse;
import static org.assertj.core.api.Assertions.assertThat;


class HistorySearchTest {


    @Test
    void getBalanceCheck() {
        var accountNumber = getCreateAccountResponse(getUserDetails()).response().as(Long.class);

        getDepositResponse(getAccountDetails(accountNumber, BigDecimal.valueOf(500)));
        var response = getBalanceCheckResponse(String.valueOf(accountNumber));
        assertThat(response.statusCode()).isEqualTo(200);
        var res = response.response().body().as(TransactionDetails.class);
        assertThat(res.getBalance()).isEqualTo(BigDecimal.valueOf(500).setScale(2, RoundingMode.FLOOR));
        assertThat(res.getUserName()).isEqualTo("testUser");

    }


    @Test
    void getHistoryOfTransactions() {
        var accountNumber = getCreateAccountResponse(getUserDetails()).response().as(Long.class);

        Stream.iterate(0, n -> n + 1)
                .limit(5)
                .forEach(x -> getDepositResponse(getAccountDetails(accountNumber, BigDecimal.valueOf(100))));

        Stream.iterate(0, n -> n + 1)
                .limit(2)
                .forEach(x -> getWithdrawalResponse(getAccountDetails(accountNumber, BigDecimal.valueOf(100))));

        var response = getTransactionHistoryResponse(String.valueOf(accountNumber));
        assertThat(response.statusCode()).isEqualTo(200);
        var res = response.body().as(new TypeRef<List<TransactionDetails>>() {
        });
        assertThat(res.size()).isEqualTo(5);
        assertThat(res.get(0).getTransactionType()).isEqualTo("withdrawal");
        assertThat(res.get(0).getBalance()).isEqualTo(BigDecimal.valueOf(300).setScale(2, RoundingMode.FLOOR));
        assertThat(res.get(0).getBeforeBalance()).isEqualTo(BigDecimal.valueOf(400).setScale(2, RoundingMode.FLOOR));

        assertThat(res.get(1).getTransactionType()).isEqualTo("withdrawal");
        assertThat(res.get(1).getBalance()).isEqualTo(BigDecimal.valueOf(400).setScale(2, RoundingMode.FLOOR));
        assertThat(res.get(1).getBeforeBalance()).isEqualTo(BigDecimal.valueOf(500).setScale(2, RoundingMode.FLOOR));


        assertThat(res.get(2).getTransactionType()).isEqualTo("deposit");
        assertThat(res.get(2).getBalance()).isEqualTo(BigDecimal.valueOf(500).setScale(2, RoundingMode.FLOOR));
        assertThat(res.get(2).getBeforeBalance()).isEqualTo(BigDecimal.valueOf(400).setScale(2, RoundingMode.FLOOR));

        assertThat(res.get(3).getTransactionType()).isEqualTo("deposit");
        assertThat(res.get(3).getBalance()).isEqualTo(BigDecimal.valueOf(400).setScale(2, RoundingMode.FLOOR));
        assertThat(res.get(3).getBeforeBalance()).isEqualTo(BigDecimal.valueOf(300).setScale(2, RoundingMode.FLOOR));

        assertThat(res.get(4).getTransactionType()).isEqualTo("deposit");
        assertThat(res.get(4).getBalance()).isEqualTo(BigDecimal.valueOf(300).setScale(2, RoundingMode.FLOOR));
        assertThat(res.get(4).getBeforeBalance()).isEqualTo(BigDecimal.valueOf(200).setScale(2, RoundingMode.FLOOR));

    }

}
