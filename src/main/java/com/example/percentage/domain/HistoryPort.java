
package com.example.percentage.domain;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface HistoryPort {
    Mono<Void> logAsync(String endpoint, Map<String, ?> params, Object response);
}
