package com.example.career.domain.member.service;

import com.example.career.domain.member.dto.ChangePasswordRequestDto;
import com.example.career.domain.member.dto.LoginRequestDto;
import com.example.career.domain.member.dto.LoginResponseDto;
import com.example.career.domain.member.dto.SignupRequestDto;
import com.example.career.domain.member.entity.Member;
import com.example.career.domain.member.repository.MemberRepository;
import com.example.career.global.common.CommonResponseDto;
import com.example.career.global.common.SuccessCode;
import com.example.career.global.error.errorcode.ErrorCode;
import com.example.career.global.error.exception.BadRequestException;
import com.example.career.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Qualifier("blacklistRedisTemplate")
    private final RedisTemplate<String, String> blacklistRedisTemplate;

    @Transactional
    public CommonResponseDto<String> signup(SignupRequestDto signupRequestDto) {
        if (memberRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new BadRequestException(ErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        Member member = signupRequestDto.toEntity(encodedPassword);
        memberRepository.save(member);

        return CommonResponseDto.success(SuccessCode.SIGNUP_SUCCESS.getMessage(), "회원가입 성공");
    }

    public CommonResponseDto<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new BadRequestException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtProvider.generateAccessToken(member.getEmail());
        String refreshToken = jwtProvider.generateRefreshToken(member.getEmail());

        return CommonResponseDto.success(SuccessCode.LOGIN_SUCCESS.getMessage(), new LoginResponseDto(accessToken, refreshToken));
    }

    @Transactional
    public CommonResponseDto<String> logout(String accessToken) {
        if (accessToken == null) {
            throw new BadRequestException(ErrorCode.INVALID_TOKEN);
        }

        jwtProvider.validateTokenOrThrow(accessToken);

        long expiration = jwtProvider.getExpiration(accessToken);
        blacklistRedisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

        return CommonResponseDto.success(SuccessCode.LOGOUT_SUCCESS.getMessage(), "로그아웃 성공");
    }

    public CommonResponseDto<String> changePassword(String token, ChangePasswordRequestDto changePasswordRequestDto) {

        String email = jwtProvider.getUsernameFromToken(token);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(changePasswordRequestDto.getCurrentPassword(), member.getPassword())) {
            throw new BadRequestException(ErrorCode.INVALID_PASSWORD);
        }

        String newEncodedPassword = passwordEncoder.encode(changePasswordRequestDto.getNewPassword());
        member.updatePassword(newEncodedPassword);

        return CommonResponseDto.success(SuccessCode.PASSWORD_CHANGE_SUCCESS.getMessage(), "비밀번호 변경 성공");
    }
}
