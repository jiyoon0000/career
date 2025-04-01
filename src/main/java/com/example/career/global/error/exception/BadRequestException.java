package com.example.career.global.error.exception;

import com.example.career.global.error.errorcode.ErrorCode;

public class BadRequestException extends CustomException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
