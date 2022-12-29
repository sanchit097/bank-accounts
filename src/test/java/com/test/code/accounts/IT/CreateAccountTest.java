package com.test.code.accounts.IT;

import com.test.code.accounts.exceptions.Error;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Random;

import static com.test.code.accounts.DataHelper.getUserDetails;
import static com.test.code.accounts.HelperMethod.getCreateAccountResponse;
import static org.assertj.core.api.Assertions.assertThat;


class CreateAccountTest {


    @Test
    void createAccountTest() {

        ExtractableResponse<Response> response = getCreateAccountResponse(getUserDetails(1111));
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(new BigDecimal(response.response().getBody().print())).isGreaterThan(BigDecimal.valueOf(0));
    }

    @Test
    void cannotCreateAccountDueToBadRequest() {
        var request = getUserDetails(1112);
        request.setSsn(null);
        ExtractableResponse<Response> response = getCreateAccountResponse(request);
        assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    void CreateAccountForExistingUser() {
        int random = new Random().nextInt(100000)+10;
        ExtractableResponse<Response> response = getCreateAccountResponse(getUserDetails(random));
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(new BigDecimal(response.response().getBody().print())).isGreaterThan(BigDecimal.valueOf(0));

        ExtractableResponse<Response> nextResponse = getCreateAccountResponse(getUserDetails(random));
        var res = nextResponse.response().getBody().as(Error.class);
        assertThat(res.getErrorCode()).isEqualTo("USER_ALREADY_EXIST");
        assertThat(res.getStatus()).isEqualTo(400);
        assertThat(res.getDescription()).isEqualTo("Given user already exists");

    }

}
