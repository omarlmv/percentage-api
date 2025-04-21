
package com.example.percentage.adapter.out.rest;

import com.example.percentage.domain.ExternalPercentagePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestPercentageAdapter implements ExternalPercentagePort {
    private final ExternalPercentageClient externalClient;
    private final PercentageCacheService cacheService;

    @Value("${external.percentage.base-url}")
    private String baseUrl;

    @Override
    public Mono<Double> getPercentage() {
        return externalClient.fetchPercentage(baseUrl)
                .doOnNext(cacheService::cachePercentage)
                .onErrorResume(cacheService::recoverFromCacheOrThrow);
    }
}
