
package com.example.percentage.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableCaching
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class CacheConfig {


    @Value("${spring.cache.caffeine.spec}")
    private String caffeineSpec;

    @PostConstruct
    public void logInitialSpec() {
        log.info("Caffeine spec activa: {}", caffeineSpec);
    }

    @Bean
    public CaffeineCacheManager cacheManager() {
        log.info("Inicializando CaffeineCacheManager con spec: {}", caffeineSpec);
        CaffeineCacheManager mgr = new CaffeineCacheManager("percentage", "bucket4j-cache");
        mgr.setCacheSpecification(caffeineSpec);

        mgr.setCaffeine(Caffeine.from(caffeineSpec)
                .recordStats()
                .removalListener((key, value, cause) ->
                        log.info("CACHE REMOVAL: key={} value={} cause={}", key, value, cause)
                )
        );
        return mgr;
    }
}