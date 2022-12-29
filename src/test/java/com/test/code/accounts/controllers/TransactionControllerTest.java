package com.test.code.accounts.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.code.accounts.models.AccountDetails;
import com.test.code.accounts.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static com.test.code.accounts.DataHelper.getAccountDetails;
import static com.test.code.accounts.DataHelper.getTransactionData;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private MockMvc mvc;

    @Test
    void saveAmount() throws Exception {
        ObjectMapper obj = new ObjectMapper();

        when(transactionService.saveAddAmount(any(AccountDetails.class)))
                .thenReturn(getTransactionData("DEPOSIT"));

        mvc.perform(MockMvcRequestBuilders
                        .post("/netBanking/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(obj.writeValueAsString(getAccountDetails(1L, BigDecimal.valueOf(100))))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionType").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionType").value("DEPOSIT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(BigDecimal.valueOf(100)));

    }

    @Test
    void withdrawAmount() throws Exception {

        ObjectMapper obj = new ObjectMapper();
        when(transactionService.getWithdrawalAmount(any(AccountDetails.class)))
                .thenReturn(getTransactionData("WITHDRAWAL"));

        mvc.perform(MockMvcRequestBuilders
                        .post("/netBanking/withdrawal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(obj.writeValueAsString(getAccountDetails(1L, BigDecimal.valueOf(100))))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionType").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionType").value("WITHDRAWAL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(BigDecimal.valueOf(100)));
    }


}