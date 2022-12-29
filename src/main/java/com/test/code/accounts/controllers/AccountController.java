package com.test.code.accounts.controllers;

import com.test.code.accounts.models.UserDetails;
import com.test.code.accounts.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/netBanking/internal/")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @PostMapping(value = "/create")
    public Long createAccount(@Valid @RequestBody UserDetails userDetails) {

        return accountService.createAccount(userDetails);

    }

}
