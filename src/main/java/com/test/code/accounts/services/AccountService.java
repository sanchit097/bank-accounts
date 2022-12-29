package com.test.code.accounts.services;

import com.test.code.accounts.domains.Account;
import com.test.code.accounts.domains.Address;
import com.test.code.accounts.exceptions.ErrorCode;
import com.test.code.accounts.exceptions.ValidationException;
import com.test.code.accounts.models.UserAddress;
import com.test.code.accounts.models.UserDetails;
import com.test.code.accounts.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public Long createAccount(UserDetails userDetails) {

        accountRepository.findAccountBySsn(userDetails.getSsn())
                .ifPresent(res -> {
                    throw new ValidationException(ErrorCode.USER_ALREADY_EXIST);
                });

        return accountRepository.save(Account.builder()
                .address(setUserAddress(userDetails.getAddress()))
                .ssn(userDetails.getSsn())
                .emailId(userDetails.getEmailId())
                .username(userDetails.getName())
                .balance(BigDecimal.ZERO)
                .build()).getAccountId();


    }

    private Address setUserAddress(UserAddress address) {

        return Address.builder().city(address.getCity())
                .country(address.getCountry())
                .houseNo(address.getHouseNo())
                .city(address.getCity())
                .country(address.getCountry())
                .pinCode(address.getPinCode())
                .build();
    }
}
