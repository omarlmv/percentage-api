package com.example.percentage.adapter.out.jpa;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Repository
@RequiredArgsConstructor
public class HistoryCustomRepositoryImpl implements HistoryCustomRepository {


    private final R2dbcEntityTemplate template;

    /*@Override
    public Flux<HistoryEntity> findAllBy(Pageable pageable) {
        String sql = String.format("SELECT * FROM history OFFSET %d LIMIT %d",
                (long) pageable.getPageNumber() * pageable.getPageSize(),
                pageable.getPageSize());
        return template.getDatabaseClient()
                .sql(sql)
                .map((row, metadata) -> HistoryEntity.builder()
                        .id(row.get("id", Long.class))
                        .endpoint(row.get("endpoint", String.class))
                        .params(row.get("params", String.class))
                        .response(row.get("response", String.class))
                        .createdAt(row.get("created_at", Instant.class))
                        .build())
                .all();
    }*/

    @Override
    public Flux<HistoryEntity> findAllBy(Pageable pageable) {
        // build a reactive query with sort, offset & limit
        return template.select(
                Query.empty()
                        .sort(Sort.by("id"))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize()),
                HistoryEntity.class
        );
    }

    @Override
    public Mono<HistoryEntity> save(HistoryEntity entity) { // <-- Nueva implementación
        return template.insert(HistoryEntity.class)
                .using(entity);
    }

   /*private final DatabaseClient databaseClient;

    @Override
    public Mono<HistoryEntity> save(HistoryEntity entity) {
        return databaseClient.sql("""
                INSERT INTO history (endpoint, params, response, created_at)
                VALUES (:endpoint, :params, :response, :createdAt)
                RETURNING id
            """)
                .bind("endpoint", entity.getEndpoint())
                .bind("params", entity.getParams())
                .bind("response", entity.getResponse())
                .bind("createdAt", entity.getCreatedAt())
                .map(row -> {
                    entity.setId(row.get("id", Long.class));
                    return entity;
                })
                .one();
    }

    @Override
    public Flux<HistoryEntity> findAllByPage(Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();

        String sql = "SELECT * FROM history ORDER BY id LIMIT " + limit + " OFFSET " + offset;

        return databaseClient.sql(sql)
                .map((row, metadata) -> HistoryEntity.builder()
                        .id(row.get("id", Long.class))
                        .endpoint(row.get("endpoint", String.class))
                        .params(row.get("params", String.class))
                        .response(row.get("response", String.class))
                        .createdAt(row.get("created_at", java.time.Instant.class))
                        .build()
                )
                .all();
    }*/
}