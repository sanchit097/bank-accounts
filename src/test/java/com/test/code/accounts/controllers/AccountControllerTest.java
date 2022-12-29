package com.test.code.accounts.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.code.accounts.models.UserAddress;
import com.test.code.accounts.models.UserDetails;
import com.test.code.accounts.services.AccountService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @MockBean
    private AccountService service;

    @Autowired
    private MockMvc mvc;


    @ParameterizedTest()
    @CsvSource({
            "'JOHN1', 'john@gmail.com',1,'cph'",
            "'JOHN', 'john.gmail.com',1,'cph'",
            "'JOHN', 'john@gmail.com',1,",
    })
    void createAccountFailure(String username, String email, Integer ssn, String city) throws Exception {

        var request = UserDetails.builder()
                .name(username)
                .emailId(email)
                .address(UserAddress.builder()
                        .streetName("street 1")
                        .city(city)
                        .pinCode("1212")
                        .country("DENMARK")
                        .houseNo("A-23")
                        .build())
                .ssn(ssn)
                .build();

        ObjectMapper obj = new ObjectMapper();
        when(service.createAccount(any(UserDetails.class)))
                .thenReturn(1L);

        mvc.perform(MockMvcRequestBuilders
                        .post("/netBanking/internal/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(obj.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
