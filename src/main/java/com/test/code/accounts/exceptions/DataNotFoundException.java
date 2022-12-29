package com.test.code.accounts.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DataNotFoundException  extends  RuntimeException {
    private final ErrorCode errorCode;
}
