package com.example.percentage.adapter.out.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
class ExternalPercentageClient {

    private final WebClient.Builder webClientBuilder;

    public Mono<Double> fetchPercentage(String baseUrl) {
        log.debug("Fetching percentage from external service: {}", baseUrl);
        return webClientBuilder.baseUrl(baseUrl)
                .build()
                .get()
                .uri("/api/percentage")
                .retrieve()
                .bodyToMono(Double.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .doBeforeRetry(rs -> log.warn("Retry #{} after error: {}",
                                rs.totalRetries() + 1, rs.failure().getMessage())));
    }
}