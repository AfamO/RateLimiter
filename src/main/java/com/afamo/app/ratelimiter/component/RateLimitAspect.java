package com.afamo.app.ratelimiter.component;

import com.afamo.app.ratelimiter.RateLimit;
import com.afamo.app.ratelimiter.exception.RateLimitExceedException;
import com.afamo.app.ratelimiter.service.RedisRateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

@Component
@Aspect
public class RateLimitAspect {
    private  final HttpServletRequest httpServletRequest;
    private final RedisRateLimiter redisRateLimiter;

    public RateLimitAspect(HttpServletRequest httpServletRequest, RedisRateLimiter redisRateLimiter) {
        this.httpServletRequest = httpServletRequest;
        this.redisRateLimiter = redisRateLimiter;
    }

    @Around("@annotation(com.afamo.app.ratelimiter.RateLimit)")
    public Object limitRate(ProceedingJoinPoint  joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);

        String clientIp = httpServletRequest.getRemoteAddr();
        String redisKey = "rateLimit:"+clientIp+":"+method.getName();
        System.out.println("Logging redisKey ..."+redisKey);
        boolean isAllowed = redisRateLimiter.isAllowed(redisKey, rateLimit.limit(), rateLimit.timeWindowSeconds());
        if(!isAllowed) throw new RateLimitExceedException("Too many requests, Limit exceeded!");
        return joinPoint.proceed();
    }
}
