package com.example.percentage.config;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
/*@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor*/
public class CacheStatsLogger {
/*
    private final CaffeineCacheManager cacheManager;

    @Scheduled(fixedRate = 60_000)
    public void dumpCacheStats() {
        var nativeCache = cacheManager
                .getCache("percentage")
                .getNativeCache();
        @SuppressWarnings("unchecked")
        com.github.benmanes.caffeine.cache.Cache<Object,Object> c =
                (com.github.benmanes.caffeine.cache.Cache<Object,Object>) nativeCache;
        CacheStats stats = c.stats();
        log.info("CACHE STATS [hits={}, misses={}, evictions={}]",
                stats.hitCount(), stats.missCount(), stats.evictionCount());
    }*/
}