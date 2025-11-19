package com.afamo.app.ratelimiter.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisRateLimiter {
    private final StringRedisTemplate redisTemplate;

    public RedisRateLimiter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public  boolean isAllowed(String key, int limit, int timeWindowSeconds) {
        Long currentCount = redisTemplate.opsForValue().increment(key);
        if (currentCount!=null && currentCount == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(timeWindowSeconds));
        }
        return currentCount <= limit;
    }
}
