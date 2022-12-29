package com.test.code.accounts.IT;

import com.test.code.accounts.exceptions.Error;
import com.test.code.accounts.models.TransactionDetails;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.test.code.accounts.DataHelper.getAccountDetails;
import static com.test.code.accounts.DataHelper.getUserDetails;
import static com.test.code.accounts.HelperMethod.getCreateAccountResponse;
import static com.test.code.accounts.HelperMethod.getDepositResponse;
import static com.test.code.accounts.HelperMethod.getWithdrawalResponse;
import static org.assertj.core.api.Assertions.assertThat;


class TransactionTest {

    @Test
    void amountDepositSuccessfully() {

        var accountNumber = getCreateAccountResponse(getUserDetails()).response().as(Long.class);

        var request = getAccountDetails(accountNumber, BigDecimal.valueOf(100));

        var response = getDepositResponse(request);
        assertThat(response.statusCode()).isEqualTo(200);
        var res = response.response().getBody().as(TransactionDetails.class);
        assertThat(res.getBalance()).isEqualTo(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.FLOOR));
        assertThat(res.getUserName()).isEqualTo("testUser");

    }

    @Test
    void amountDepositNoAccountFound() {

        var request = getAccountDetails(999999L, BigDecimal.valueOf(100));

        var response = getDepositResponse(request);
        assertThat(response.statusCode()).isEqualTo(404);
        var res = response.response().getBody().as(Error.class);
        assertThat(res.getErrorCode()).isEqualTo("ACCOUNT_NOT_FOUND");
        assertThat(res.getStatus()).isEqualTo(404);
        assertThat(res.getDescription()).isEqualTo("Given account id not found");

    }

    @Test
    void amountWidthDrawSuccessfully() {

        var accountNumber = getCreateAccountResponse(getUserDetails()).response().as(Long.class);
        getDepositResponse(getAccountDetails(accountNumber, BigDecimal.valueOf(10000)));

        var request = getAccountDetails(accountNumber, BigDecimal.valueOf(100));

        var response = getWithdrawalResponse(request);
        assertThat(response.statusCode()).isEqualTo(200);
        var res = response.response().getBody().as(TransactionDetails.class);
        assertThat(res.getBalance()).isEqualTo(BigDecimal.valueOf(9900.00).setScale(2, RoundingMode.FLOOR));
        assertThat(res.getUserName()).isEqualTo("testUser");

    }

    @Test
    void amountWithDrawNoAccountFound() {

        var request = getAccountDetails(999999L, BigDecimal.valueOf(100));

        var response = getWithdrawalResponse(request);
        assertThat(response.statusCode()).isEqualTo(404);
        var res = response.response().getBody().as(Error.class);
        assertThat(res.getErrorCode()).isEqualTo("ACCOUNT_NOT_FOUND");
        assertThat(res.getStatus()).isEqualTo(404);
        assertThat(res.getDescription()).isEqualTo("Given account id not found");

    }

    @Test
    void amountWithDrawMoreThanBalance() {

        var accountNumber = getCreateAccountResponse(getUserDetails()).response().as(Long.class);

        getDepositResponse(getAccountDetails(accountNumber, BigDecimal.valueOf(100)));
        var request = getAccountDetails(accountNumber, BigDecimal.valueOf(200));
        var response = getWithdrawalResponse(request);
        assertThat(response.statusCode()).isEqualTo(400);
        var res = response.response().getBody().as(Error.class);
        assertThat(res.getErrorCode()).isEqualTo("INCORRECT_AMOUNT");
        assertThat(res.getStatus()).isEqualTo(400);
        assertThat(res.getDescription()).isEqualTo("Given amount is greater than available balance");

    }
}
