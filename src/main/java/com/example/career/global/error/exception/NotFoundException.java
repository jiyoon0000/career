package com.example.career.global.error.exception;

import com.example.career.global.error.errorcode.ErrorCode;

public class NotFoundException extends CustomException {

    public NotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
