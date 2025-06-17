package com.example.career.domain.member.service;

import com.example.career.domain.member.dto.ChangePasswordRequestDto;
import com.example.career.domain.member.dto.LoginRequestDto;
import com.example.career.domain.member.dto.LoginResponseDto;
import com.example.career.domain.member.dto.SignupRequestDto;
import com.example.career.domain.member.entity.Member;
import com.example.career.domain.member.repository.MemberRepository;
import com.example.career.global.error.errorcode.ErrorCode;
import com.example.career.global.error.exception.BadRequestException;
import com.example.career.global.jwt.JwtProvider;
import com.example.career.global.util.RedisUtil;
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
    private final RedisUtil redisUtil;

    @Qualifier("blacklistRedisTemplate")
    private final RedisTemplate<String, String> blacklistRedisTemplate;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        if (memberRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new BadRequestException(ErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        Member member = signupRequestDto.toEntity(encodedPassword);
        memberRepository.save(member);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new BadRequestException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtProvider.generateAccessToken(member.getEmail());
        String refreshToken = jwtProvider.generateRefreshToken(member.getEmail());

        long refreshTokenExpiry = jwtProvider.getRefreshTokenExpiry();
        long accessTokenExpiry = jwtProvider.getRefreshTokenExpiry();

        redisUtil.saveRefreshToken(member.getId(), refreshToken, refreshTokenExpiry);
        redisUtil.saveAccessToken(member.getId(), accessToken, accessTokenExpiry);

        return new LoginResponseDto(accessToken, refreshToken);
    }

    @Transactional
    public void logout(String accessToken) {
        if (accessToken == null) {
            throw new BadRequestException(ErrorCode.INVALID_TOKEN);
        }

        jwtProvider.validateTokenOrThrow(accessToken);

        String email = jwtProvider.getUsernameFromToken(accessToken);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));

        redisUtil.deleteRefreshToken(member.getId());
        redisUtil.deleteAccessToken(member.getId());

        long expiration = jwtProvider.getExpiration(accessToken);
        blacklistRedisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
    }

    @Transactional
    public void changePassword(String token, ChangePasswordRequestDto changePasswordRequestDto) {

        String email = jwtProvider.getUsernameFromToken(token);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(changePasswordRequestDto.getCurrentPassword(), member.getPassword())) {
            throw new BadRequestException(ErrorCode.INVALID_PASSWORD);
        }

        String newEncodedPassword = passwordEncoder.encode(changePasswordRequestDto.getNewPassword());
        member.updatePassword(newEncodedPassword);
    }

    public LoginResponseDto reissueToken(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new BadRequestException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String email = jwtProvider.getUsernameFromToken(refreshToken);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));

        String savedRefreshToken = redisUtil.getRefreshToken(member.getId());

        if (savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)) {
            throw new BadRequestException(ErrorCode.DUPLICATE_LOGIN_DETECTED);
        }

        String newAccessToken = jwtProvider.generateAccessToken(email);
        String newRefreshToken = jwtProvider.generateRefreshToken(email);

        redisUtil.saveRefreshToken(member.getId(), newRefreshToken, jwtProvider.getRefreshTokenExpiry());

        return new LoginResponseDto(newAccessToken, newRefreshToken);
    }

}
