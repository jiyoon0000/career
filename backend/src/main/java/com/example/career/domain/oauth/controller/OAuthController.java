package com.example.career.domain.oauth.controller;

import com.example.career.domain.member.dto.LoginResponseDto;
import com.example.career.domain.oauth.service.KakaoOAuthService;
import com.example.career.global.common.CommonResponseDto;
import com.example.career.global.common.SuccessCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "소셜 로그인 API")
public class OAuthController {

    private final KakaoOAuthService kakaoOAuthService;

    @GetMapping("kakao/callback")
    public ResponseEntity<CommonResponseDto<LoginResponseDto>> kakaoLogin(@RequestParam String code) {
        LoginResponseDto loginResponseDto = kakaoOAuthService.kakaoLogin(code);

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.LOGIN_SUCCESS, loginResponseDto));
    }
}
