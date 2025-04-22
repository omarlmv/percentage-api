package com.example.percentage.adapter.out.rest;

import com.example.percentage.exception.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class PercentageCacheService {
    private static final String CACHE_NAME = "percentage";
    private static final String CACHE_KEY  = "fixedPercentage";
    private final CacheManager cacheManager;

    public Mono<Double> getCachedPercentage() {
        var cache = cacheManager.getCache(CACHE_NAME);
        if (cache == null) return Mono.empty();
        var valueWrapper = cache.get(CACHE_KEY);
        if (valueWrapper != null && valueWrapper.get() instanceof Double cached) {
            log.debug("CACHE HIT: {}", cached);
            return Mono.just(cached);
        }
        log.debug("CACHE MISS: No value found for key '{} or expired'", CACHE_KEY);
        return Mono.empty();
    }

    public void cachePercentage(Double percentage) {
        var cache = cacheManager.getCache(CACHE_NAME);
        if (cache != null) {
            cache.put(CACHE_KEY, percentage);
            log.debug("Cached new percentage: {}", percentage);
        }
    }

    public Mono<Double> recoverFromCacheOrThrow(Throwable ex) {
        log.warn("External service failed after retries: {}", ex.getMessage());
        return getCachedPercentage()
                .switchIfEmpty(Mono.error(new ServiceUnavailableException(
                        "Percentage service unavailable and no cached value"
                )));
    }

}
