package com.example.career.domain.member.controller;

import com.example.career.domain.member.dto.ChangePasswordRequestDto;
import com.example.career.domain.member.dto.LoginRequestDto;
import com.example.career.domain.member.dto.LoginResponseDto;
import com.example.career.domain.member.dto.SignupRequestDto;
import com.example.career.domain.member.service.MemberService;
import com.example.career.global.auth.service.EmailAuthService;
import com.example.career.global.common.CommonResponseDto;
import com.example.career.global.common.SuccessCode;
import com.example.career.global.error.errorcode.ErrorCode;
import com.example.career.global.error.exception.CustomException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "회원 관련 API")
public class MemberController {

    private final MemberService memberService;
    private final EmailAuthService emailAuthService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto<String>> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {

        if (!emailAuthService.isVerified(signupRequestDto.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_NOT_VERIFIED);
        }

        memberService.signup(signupRequestDto);

        emailAuthService.clearVerifiedFlag(signupRequestDto.getEmail());

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.SIGNUP_SUCCESS, null));
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

        log.info("Authorization header: {}", bearer);

        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }

        return null;
    }

    @PatchMapping("/password")
    public ResponseEntity<CommonResponseDto<String>> changePassword(HttpServletRequest httpServletRequest,
                                                                    @Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        String token = resolveToken(httpServletRequest);

        return ResponseEntity.ok(memberService.changePassword(token, changePasswordRequestDto));
    }
}