package com.test.code.accounts.services;

import com.test.code.accounts.exceptions.DataNotFoundException;
import com.test.code.accounts.exceptions.ErrorCode;
import com.test.code.accounts.models.AccountDetails;
import com.test.code.accounts.models.TransactionDetails;
import com.test.code.accounts.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TransactionService {

    private final AccountRepository accountRepository;

    private final HistoryService historyService;

    private static final String WITHDRAWAL = "withdrawal";
    private static final String DEPOSIT = "deposit";


    public TransactionDetails getWithdrawalAmount(AccountDetails accountDetails) {
        return accountRepository.findById(accountDetails.getAccountNumber())
                .map(res -> Pair.of(res.getBalance(), accountRepository.save(res.withdrawBalance(accountDetails.getAmount()))))
                .map(res -> historyService.saveHistory(res, WITHDRAWAL))
                .map(res -> TransactionDetails.builder()
                        .balance(res.getAfterBalance())
                        .userName(res.getUsername()).build())
                .orElseThrow(() -> new DataNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND));

    }

    public TransactionDetails saveAddAmount(AccountDetails accountDetails) {

        return accountRepository.findById(accountDetails.getAccountNumber())
                .map(res -> Pair.of(res.getBalance(), accountRepository.save(res.addBalance(accountDetails.getAmount()))))
                .map(res -> historyService.saveHistory(res, DEPOSIT))
                .map(res -> TransactionDetails.builder()
                        .balance(res.getAfterBalance())
                        .userName(res.getUsername()).build())
                .orElseThrow(() -> new DataNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND));

    }

    public TransactionDetails getBalanceDetails(Long accountId) {

        return accountRepository.findById(accountId)
                .map(res -> TransactionDetails.builder().balance(res.getBalance())
                        .userName(res.getUsername()).build())
                .orElseThrow(() -> new DataNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND));
    }
}
