
package com.example.percentage.adapter.out.jpa;

import com.example.percentage.domain.HistoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

//@Profile("local")  // Solo en perfil "local"
@Component
@RequiredArgsConstructor
@Slf4j
public class HistoryJpaAdapter implements HistoryPort {

    private final HistoryRepository repo;
    private final ObjectMapper mapper;

    @Override
    public Mono<Void> logAsync(String endpoint, Map<String, ?> params, Object response) {
        log.debug("logAsync storing history for endpoint {}", endpoint);
        try {
            HistoryEntity entity = HistoryEntity.builder()
                    .endpoint(endpoint)
                    .params(mapper.writeValueAsString(params))
                    .response(mapper.writeValueAsString(response))
                    .createdAt(Instant.now())
                    .build();
            return repo.save(entity)
                    .doOnSuccess(id -> log.info("History saved successfully with ID: {}", id))
                    .doOnError(e -> log.error("Error saving history: {}", e.getMessage(), e))
                    .then();
        } catch (Exception e) {
            log.warn("Failed to save history: {}", e.getMessage());
            return Mono.empty();
        }
    }
}
