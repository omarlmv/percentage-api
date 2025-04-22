
package com.example.percentage.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "ratelimit")
@Getter
@Setter
@Slf4j
public class RateLimitWebFilter implements WebFilter {

    /**
     * Total de tokens en el bucket (ej: 5)
     */
    private int capacity;

    /**
     * Tokens que se recargan cada periodo (ej: 5)
     */
    private int refillTokens;

    /**
     * Periodo de recarga (ej: PT1M para 1 minuto)
     */
    private Duration refillPeriod;

    private Bucket bucket;

    @PostConstruct
    public void initBucket() {
        Bandwidth limit = Bandwidth.classic(
                capacity,
                Refill.greedy(refillTokens, refillPeriod)
        );
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();
        log.info("Request received: path={}, method={}", path, method);

        // Permitir libremente Swagger + API Docs + Preflight (OPTIONS)
        if (path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/webjars")
                || "OPTIONS".equalsIgnoreCase(method)) {
            log.debug("Request allowed without rate limiting: path={}, method={}", path, method);
            return chain.filter(exchange);
        }

        if (bucket.tryConsume(1)) {
            log.debug("Request allowed: path={}, method={}", path, method);
            return chain.filter(exchange);
        }
        log.warn("Request rate-limited: path={}, method={}", path, method);
        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.TOO_MANY_REQUESTS);
        return exchange.getResponse().setComplete();
    }
}
