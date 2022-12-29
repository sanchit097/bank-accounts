package com.test.code.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.code.accounts.models.AccountDetails;
import com.test.code.accounts.models.UserDetails;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.SneakyThrows;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;

public class HelperMethod {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static ExtractableResponse<Response> getCreateAccountResponse(UserDetails userDetails) {
        RestAssured.reset();
        RestAssured.baseURI = "http://127.0.0.1:8081/netBanking";
        var response = with()
                .body(objectMapper.writeValueAsString(userDetails))
                .contentType(ContentType.JSON)
                .when()
                .request(Method.POST, "/internal/create")
                .then()
                .extract();
        Thread.sleep(100);
        return response;
    }


    @SneakyThrows
    public static ExtractableResponse<Response> getWithdrawalResponse(AccountDetails accountDetails) {
        RestAssured.reset();
        RestAssured.baseURI = "http://127.0.0.1:8081/netBanking";
        var response = with()
                .body(objectMapper.writeValueAsString(accountDetails))
                .contentType(ContentType.JSON)
                .when()
                .request(Method.POST, "/withdrawal")
                .then()
                .extract();
        Thread.sleep(100);
        return response;
    }

    @SneakyThrows
    public static ExtractableResponse<Response> getDepositResponse(AccountDetails accountDetails) {
        RestAssured.reset();
        RestAssured.baseURI = "http://127.0.0.1:8081/netBanking";
        var response = with()
                .body(objectMapper.writeValueAsString(accountDetails))
                .contentType(ContentType.JSON)
                .when()
                .request(Method.POST, "/deposit")
                .then()
                .extract();
        Thread.sleep(100);
        return response;
    }

    @SneakyThrows
    public static ExtractableResponse<Response> getBalanceCheckResponse(String accountId) {
        RestAssured.reset();
        RestAssured.baseURI = "http://127.0.0.1:8081/netBanking";
        var  response = get("{accountId}/showBalance/", accountId)
                .then()
                .extract();
        Thread.sleep(100);
        return response;
    }

    @SneakyThrows
    public static ExtractableResponse<Response> getTransactionHistoryResponse(String accountId) {
        RestAssured.reset();
        RestAssured.baseURI = "http://127.0.0.1:8081/netBanking";
        var  response = get("{accountId}/showHistory/", accountId)
                .then()
                .extract();
        Thread.sleep(100);
        return response;
    }
}
