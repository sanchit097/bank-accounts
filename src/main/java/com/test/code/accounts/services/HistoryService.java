package com.test.code.accounts.services;

import com.test.code.accounts.domains.Account;
import com.test.code.accounts.domains.History;
import com.test.code.accounts.models.TransactionDetails;
import com.test.code.accounts.repositories.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;


    public List<TransactionDetails> getTransactionHistory(Long accountId) {

        return historyRepository.findHistoryByAccountId(accountId)
                .stream().map(res -> TransactionDetails.builder().userName(res.getUsername())
                        .balance(res.getAfterBalance())
                        .beforeBalance(res.getBeforeBalance())
                        .transactionType(res.getTransactionType()).build())
                .collect(Collectors.toList());
    }

    public History saveHistory(Pair<BigDecimal, Account> tuple, String type) {

        return historyRepository.save(History.builder().accountId(tuple.getSecond().getAccountId())
                .afterBalance(tuple.getSecond().getBalance())
                .beforeBalance(tuple.getFirst())
                .transactionType(type)
                .username(tuple.getSecond().getUsername())
                .build());
    }


}
