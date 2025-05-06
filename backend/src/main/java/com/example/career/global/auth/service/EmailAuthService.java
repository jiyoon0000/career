package com.example.career.global.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

    private static final long EXPIRE_TIME = 5 * 60L;

    @Qualifier("emailVerifiedRedisTemplate")
    private final RedisTemplate<String, String> redisTemplate;

    private final MailService mailService;

    public void sendAuthCode(String email) {
        String authCode = createAuthCode();

        redisTemplate.opsForValue().set(email, authCode, Duration.ofSeconds(EXPIRE_TIME));
        mailService.sendEMail(email, "[Careeroom] 인증코드입니다.", "인증코드: " + authCode);
    }

    private String createAuthCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    public boolean verifyAuthCode(String email, String code) {
        String saved = redisTemplate.opsForValue().get(email);

        boolean isMatch = saved != null && saved.equals(code);

        if (isMatch) {
            saveVerifiedFlag(email);
        }

        return isMatch;
    }

    public boolean isVerified(String email) {
        String verified = redisTemplate.opsForValue().get("email_verified: " + email);

        return "true".equals(verified);
    }

    public void saveVerifiedFlag(String email) {
        redisTemplate.opsForValue().set("email_verified: " + email, "true", Duration.ofMinutes(10));
    }

    public void clearVerifiedFlag(String email) {
        redisTemplate.delete("email_verified: " + email);
    }
}
