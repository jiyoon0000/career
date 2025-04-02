package com.example.career.domain.member.controller;

import com.example.career.domain.member.dto.LoginRequestDto;
import com.example.career.domain.member.dto.LoginResponseDto;
import com.example.career.domain.member.dto.SignupRequestDto;
import com.example.career.domain.member.service.MemberService;
import com.example.career.global.common.CommonResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "회원 관련 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto<String>> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return ResponseEntity.ok(memberService.signup(signupRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(memberService.login(loginRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponseDto<String>> logout(HttpServletRequest httpServletRequest) {
        String token = resolveToken(httpServletRequest);
        return ResponseEntity.ok(memberService.logout(token));
    }

    private String resolveToken(HttpServletRequest httpServletRequest) {
        String bearer = httpServletRequest.getHeader("Authorization");

        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }

        return null;
    }
}
