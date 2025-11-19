package com.afamo.app.ratelimiter.exception;

public class RateLimitExceedException extends Throwable {
    public RateLimitExceedException(String s) {
        super(s);
    }
}
