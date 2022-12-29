package com.test.code.accounts.IT;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.test.code.accounts.DataHelper.getUserDetails;
import static com.test.code.accounts.HelperMethod.getCreateAccountResponse;
import static org.assertj.core.api.Assertions.assertThat;


class CreateAccountTest {


    @Test
    void createAccountTest() {

        ExtractableResponse<Response> response = getCreateAccountResponse(getUserDetails());
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(new BigDecimal(response.response().getBody().print())).isGreaterThan(BigDecimal.valueOf(0));
    }

    @Test
    void cannotCreateAccountDueToBadRequest() {
        var request = getUserDetails();
        request.setSsn(null);
        ExtractableResponse<Response> response = getCreateAccountResponse(request);
        assertThat(response.statusCode()).isEqualTo(400);
    }


}
