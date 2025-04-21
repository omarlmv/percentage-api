
package com.example.percentage.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
//@ConditionalOnProperty(prefix = "ratelimit", name = "enabled", havingValue = "true")
@ConfigurationProperties(prefix = "ratelimit")
@Getter
@Setter
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

    // Después de bindear las propiedades, construimos el bucket
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
        if (bucket.tryConsume(1)) {
            return chain.filter(exchange);
        }
        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.TOO_MANY_REQUESTS);
        return exchange.getResponse().setComplete();
    }

    /*


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (bucket.tryConsume(1)) {
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }
    }*/
}
