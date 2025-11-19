package com.afamo.app.ratelimiter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.afamo.app.ratelimiter.exception.RateLimitExceedException;


@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(RateLimitExceedException.class)
    public ResponseEntity<String> handleRateLimitExceedException(RateLimitExceedException rateLimitExceedException) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(rateLimitExceedException.getMessage());
    }
}
