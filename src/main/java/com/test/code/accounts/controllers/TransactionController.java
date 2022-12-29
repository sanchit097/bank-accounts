package com.test.code.accounts.controllers;

import com.test.code.accounts.models.AccountDetails;
import com.test.code.accounts.models.TransactionDetails;
import com.test.code.accounts.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/netBanking")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public TransactionDetails saveAmount(@Valid @RequestBody AccountDetails accountDetails) {
        return transactionService.saveAddAmount(accountDetails);
    }

    @PostMapping("/withdrawal")
    public TransactionDetails withdrawAmount(@Valid @RequestBody AccountDetails accountDetails) {
        return transactionService.getWithdrawalAmount(accountDetails);
    }


}
