package com.test.code.accounts.services;

import com.test.code.accounts.domains.Account;
import com.test.code.accounts.domains.History;
import com.test.code.accounts.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.test.code.accounts.DataHelper.getAccountData;
import static com.test.code.accounts.DataHelper.getAccountDetails;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransactionServiceTest {


    private TransactionService transactionService;

    @MockBean
    private HistoryService historyService;

    @MockBean
    private AccountRepository accountRepository;


    @BeforeEach
    public void setUp() {

        this.transactionService = new TransactionService(accountRepository, historyService);
    }


    @Test
    void getWithdrawalAmount() {

        //given
        when(accountRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getAccountData(BigDecimal.valueOf(1000))));

        when(accountRepository.save(any(Account.class)))
                .thenReturn(getAccountData(BigDecimal.valueOf(900)));

        when(historyService.saveHistory(any(), anyString()))
                .thenReturn(History.builder().accountId(1L)
                        .username("testUser")
                        .beforeBalance(BigDecimal.valueOf(1000))
                        .afterBalance(BigDecimal.valueOf(900))
                        .transactionType("WITHDRAWAL")
                        .build()
                );

        //when
        var res = transactionService.getWithdrawalAmount(getAccountDetails(1L, BigDecimal.valueOf(100)));

        // then
        assertThat(res.getBalance()).isEqualTo(BigDecimal.valueOf(900));
        assertThat(res.getUserName()).isEqualTo("testUser");

    }


}
