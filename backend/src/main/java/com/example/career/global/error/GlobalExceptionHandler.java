package com.example.career.global.error;

import com.example.career.global.error.errorcode.ErrorCode;
import com.example.career.global.error.exception.CustomException;
import com.example.career.global.error.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e, HttpServletRequest httpServletRequest) {
        return ErrorResponse.toResponseEntity(
                e.getErrorCode(),
                httpServletRequest.getRequestURI(),
                httpServletRequest.getMethod()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest httpServletRequest) {
        return ErrorResponse.toResponseEntity(
                ErrorCode.INTERNAL_SERVER_ERROR,
                httpServletRequest.getRequestURI(),
                httpServletRequest.getMethod()
        );
    }
}
