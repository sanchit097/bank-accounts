package com.test.code.accounts.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ValidationException extends RuntimeException {
    private final ErrorCode errorCode;
}
