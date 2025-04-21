package com.example.percentage.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties("rate-limit")
public class RateLimitProperties {
    private int capacity;
    private Duration refillDuration;
}
