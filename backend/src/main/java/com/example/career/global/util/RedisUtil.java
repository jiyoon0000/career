package com.example.career.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;

    public void saveRefreshToken(Long memberId, String token, long expirationMillis) {
        String key = "refreshToken:" + memberId;
        redisTemplate.opsForValue().set(key, token, expirationMillis, TimeUnit.MILLISECONDS);
    }

    public String getRefreshToken(Long memberId) {
        String key = "refreshToken:" + memberId;
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteRefreshToken(Long memberId) {
        String key = "refreshToken:" + memberId;
        redisTemplate.delete(key);
    }
}
