package com.example.career.domain.member.controller;

import com.example.career.domain.member.dto.ChangePasswordRequestDto;
import com.example.career.domain.member.dto.LoginRequestDto;
import com.example.career.domain.member.dto.LoginResponseDto;
import com.example.career.domain.member.dto.PasswordResetEmailRequestDto;
import com.example.career.domain.member.dto.PasswordResetRequestDto;
import com.example.career.domain.member.dto.PasswordResetVerifyRequestDto;
import com.example.career.domain.member.dto.SignupRequestDto;
import com.example.career.domain.member.dto.TokenReissueRequestDto;
import com.example.career.domain.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto<Void>> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {

        if (!emailAuthService.isVerified(signupRequestDto.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_NOT_VERIFIED);
        }

        memberService.signup(signupRequestDto);

        emailAuthService.clearVerifiedFlag(signupRequestDto.getEmail());

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.SIGNUP_SUCCESS, null));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = memberService.login(loginRequestDto);

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.LOGIN_SUCCESS, loginResponseDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponseDto<Void>> logout(HttpServletRequest httpServletRequest) {
        String token = resolveToken(httpServletRequest);

        memberService.logout(token);

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.LOGOUT_SUCCESS, null));
    }

    private String resolveToken(HttpServletRequest httpServletRequest) {
        String bearer = httpServletRequest.getHeader("Authorization");

        log.info("Authorization header: {}", bearer);

        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }

        return null;
    }

    @PostMapping("/refresh")
    public ResponseEntity<CommonResponseDto<LoginResponseDto>> refresh(@Valid @RequestBody TokenReissueRequestDto tokenReissueRequestDto) {
        LoginResponseDto loginResponseDto = memberService.reissueToken(tokenReissueRequestDto.getRefreshToken());

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.TOKEN_REISSUE_SUCCESS, loginResponseDto));
    }

    @PostMapping("/password/reset/send")
    public ResponseEntity<CommonResponseDto<Void>> sendResetCode(@Valid @RequestBody PasswordResetEmailRequestDto passwordResetEmailRequestDto) {
        memberService.sendPasswordResetCode(passwordResetEmailRequestDto.getEmail());

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.EMAIL_SEND_SUCCESS, null));
    }

    @PostMapping("/password/reset/verify")
    public ResponseEntity<CommonResponseDto<Void>> verifyResetCode(@Valid @RequestBody PasswordResetVerifyRequestDto passwordResetVerifyRequestDto) {
        memberService.verifyPasswordResetCode(passwordResetVerifyRequestDto.getEmail(), passwordResetVerifyRequestDto.getCode());

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.EMAIL_VERIFY_SUCCESS, null));
    }

    @PostMapping("/password/reset/confirm")
    public ResponseEntity<CommonResponseDto<Void>> resetPassword(@Valid @RequestBody PasswordResetRequestDto passwordResetRequestDto) {
        memberService.resetPassword(passwordResetRequestDto.getEmail(), passwordResetRequestDto.getNewPassword());

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.PASSWORD_RESET_SUCCESS, null));
    }

    @PatchMapping("/password")
    public ResponseEntity<CommonResponseDto<Void>> changePassword(HttpServletRequest httpServletRequest,
                                                                  @Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        String token = resolveToken(httpServletRequest);

        memberService.changePassword(token, changePasswordRequestDto);

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.PASSWORD_CHANGE_SUCCESS, null));
    }
}