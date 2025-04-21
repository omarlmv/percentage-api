package com.example.percentage.adapter.out.jpa;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HistoryCustomRepository {
    Mono<HistoryEntity> save(HistoryEntity entity);  // 👈 Añades save
    Flux<HistoryEntity> findAllBy(Pageable pageable);
}
