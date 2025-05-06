package com.example.career.global.auth.controller;

import com.example.career.global.auth.dto.EmailRequestDto;
import com.example.career.global.auth.dto.EmailVerifyDto;
import com.example.career.global.auth.service.EmailAuthService;
import com.example.career.global.common.CommonResponseDto;
import com.example.career.global.common.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/email")
public class AuthController {

    private final EmailAuthService emailAuthService;

    @PostMapping("/send")
    public ResponseEntity<CommonResponseDto<Void>> sendEmail(@RequestBody EmailRequestDto emailRequestDto) {
        emailAuthService.sendAuthCode(emailRequestDto.getEmail());

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.EMAIL_SEND_SUCCESS, null));
    }

    @PostMapping("/verify")
    public ResponseEntity<CommonResponseDto<Boolean>> verifyCode(@RequestBody EmailVerifyDto emailVerifyDto) {
        boolean result = emailAuthService.verifyAuthCode(emailVerifyDto.getEmail(), emailVerifyDto.getCode());

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.EMAIL_VERIFY_SUCCESS, result));
    }
}
