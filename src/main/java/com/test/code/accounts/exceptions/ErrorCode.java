package com.test.code.accounts.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
    ACCOUNT_NOT_FOUND("Given account id not found"),
    INCORRECT_AMOUNT("Given amount is greater than available balance")
    ;
    String description;
}
