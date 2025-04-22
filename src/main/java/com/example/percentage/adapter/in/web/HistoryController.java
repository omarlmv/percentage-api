
package com.example.percentage.adapter.in.web;

import com.example.percentage.adapter.out.jpa.HistoryRepository;
import com.example.percentage.api.HistoryApi;
import com.example.percentage.model.HistoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZoneOffset;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HistoryController implements HistoryApi {

    private final HistoryRepository repo;

    @Override
    public Mono<ResponseEntity<Flux<com.example.percentage.model.HistoryDto>>> getHistory(
            Integer page,
            Integer size,
            ServerWebExchange exchange
    ) {
        int pageNumber = Math.max(0, page != null ? page : 0);
        int pageSize = (size != null && size > 0) ? size : 10;
        long offset = (long) pageNumber * pageSize;

        log.debug("Method: getHistory, Parameters: {{\"page\": {}, \"size\": {}, \"offset\": {}}}",
                pageNumber, pageSize, offset);

        Flux<HistoryDto> dtos = repo.findPaged(pageSize, offset)
                .map(h -> new HistoryDto(
                        h.getId(), h.getEndpoint(), h.getParams(), h.getResponse(),
                        h.getCreatedAt().atOffset(ZoneOffset.UTC)
                ));
        return Mono.just(ResponseEntity.ok(dtos));
    }
}
