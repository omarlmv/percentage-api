package com.example.percentage.adapter.in.web;

import com.example.percentage.adapter.out.jpa.HistoryEntity;
import com.example.percentage.adapter.out.jpa.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.mockito.Mockito.*;

class HistoryControllerTest {

    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private HistoryController historyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnHistorySuccessfully() {
        HistoryEntity entity = new HistoryEntity(1L, "endpoint", "params", "response", Instant.now());

        when(historyRepository.findPaged(anyInt(), anyLong())).thenReturn(Flux.just(entity));

        StepVerifier.create(historyController.getHistory(0, 10, null))
                .expectNextMatches(response -> response.getStatusCode().is2xxSuccessful()
                        && response.getBody() != null)
                .verifyComplete();

        verify(historyRepository, times(1)).findPaged(anyInt(), anyLong());
    }

    @Test
    void shouldHandleEmptyHistory() {
        when(historyRepository.findPaged(anyInt(), anyLong())).thenReturn(Flux.empty());

        StepVerifier.create(historyController.getHistory(0, 10, null))
                .expectNextMatches(response -> response.getStatusCode().is2xxSuccessful()
                        && response.getBody() != null)
                .verifyComplete();

        verify(historyRepository, times(1)).findPaged(anyInt(), anyLong());
    }
}
