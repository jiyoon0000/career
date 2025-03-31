package com.example.career.global.error.exception;

import com.example.career.global.error.errorcode.ErrorCode;

public class BadRequestException extends CustomException {

    public BadRequestException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
