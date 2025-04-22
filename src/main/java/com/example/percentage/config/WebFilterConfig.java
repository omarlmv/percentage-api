package com.example.percentage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebFilterConfig {

    @Bean
    public RateLimitWebFilter rateLimitWebFilter() {
        return new RateLimitWebFilter();
    }
}