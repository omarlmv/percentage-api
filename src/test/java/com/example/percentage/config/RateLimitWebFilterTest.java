package com.example.percentage.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.mockito.Mockito.*;

class RateLimitWebFilterTest {

    @InjectMocks
    private RateLimitWebFilter rateLimitWebFilter;

    @Mock
    private WebFilterChain webFilterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rateLimitWebFilter.setCapacity(2);
        rateLimitWebFilter.setRefillTokens(2);
        rateLimitWebFilter.setRefillPeriod(Duration.ofMinutes(1));
        rateLimitWebFilter.initBucket();
    }

    @Test
    void shouldAllowRequestWhenBucketHasTokens() {
        MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").build());

        when(webFilterChain.filter(exchange)).thenReturn(Mono.empty());

        StepVerifier.create(rateLimitWebFilter.filter(exchange, webFilterChain))
                .verifyComplete();

        verify(webFilterChain, times(1)).filter(exchange);
    }

    @Test
    void shouldRejectRequestWhenBucketIsEmpty() {
        MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").build());

        rateLimitWebFilter.getBucket().tryConsume(2);

        StepVerifier.create(rateLimitWebFilter.filter(exchange, webFilterChain))
                .verifyComplete();

        MockServerHttpResponse response = exchange.getResponse();
        assert response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS;

        verify(webFilterChain, times(0)).filter(exchange);
    }
}