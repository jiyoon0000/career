package com.example.career.global.error.exception;

import com.example.career.global.error.errorcode.ErrorCode;

public class UnAuthorizedException extends CustomException {

    public UnAuthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }
}
