package com.example.career.global.error.exception;

import com.example.career.global.error.errorcode.ErrorCode;

public class ForbiddenException extends CustomException {

    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN);
    }
}
