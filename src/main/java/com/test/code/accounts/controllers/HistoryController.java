package com.test.code.accounts.controllers;

import com.test.code.accounts.models.TransactionDetails;
import com.test.code.accounts.services.HistoryService;
import com.test.code.accounts.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/netBanking/")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;
    private final TransactionService transactionService;


    @GetMapping("{accountId}/showBalance")
    public TransactionDetails getBalance(@Valid @PathVariable("accountId") Long accountId) {
        return transactionService.getBalanceDetails(accountId);
    }

    @GetMapping("{accountId}/showHistory")
    public List<TransactionDetails> getTransactionHistory(@Valid @PathVariable("accountId") Long accountId) {
        return historyService.getTransactionHistory(accountId);
    }
}
