package com.afamo.app.ratelimiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.afamo.app.ratelimiter.RateLimit;

@SpringBootApplication
@RestController
public class RateLimiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RateLimiterApplication.class, args);
	}

    @RateLimit(limit = 5,timeWindowSeconds = 10)
    @GetMapping("/api/data")
    public String getData (){
        return "Hello World!";
    }
}


