package com.example.career.global.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonResponseDto<T> {

    private int status;
    private String message;
    private T data;

    private CommonResponseDto(HttpStatus status, String message, T data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    private CommonResponseDto(HttpStatus status, T data) {
        this.status = status.value();
        this.message = status.getReasonPhrase();
        this.data = data;
    }

    public static <T> CommonResponseDto<T> success(SuccessCode successCode, T data) {
        return new CommonResponseDto<>(HttpStatus.OK, successCode.getMessage(), data);
    }

    public static <T> CommonResponseDto<T> error(HttpStatus status, String message) {
        return new CommonResponseDto<>(status, message, null);
    }
}
