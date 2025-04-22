
package com.example.percentage.adapter.out.jpa;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface HistoryRepository extends ReactiveCrudRepository<HistoryEntity, Long> {
    @Query("""
        SELECT *
        FROM history
        ORDER BY created_at DESC
        LIMIT :limit OFFSET :offset
    """)
    Flux<HistoryEntity> findPaged(int limit, long offset);
}