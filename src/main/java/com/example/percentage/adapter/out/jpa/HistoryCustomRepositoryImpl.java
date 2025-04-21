package com.example.percentage.adapter.out.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class HistoryCustomRepositoryImpl implements HistoryCustomRepository {
    private final R2dbcEntityTemplate template;

    @Override
    public Flux<HistoryEntity> findAllBy(Pageable pageable) {
        return template.select(
                Query.empty()
                        .sort(Sort.by("id"))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize()),
                HistoryEntity.class
        );
    }

    @Override
    public Mono<HistoryEntity> save(HistoryEntity entity) {
        return template.insert(HistoryEntity.class)
                .using(entity);
    }
}