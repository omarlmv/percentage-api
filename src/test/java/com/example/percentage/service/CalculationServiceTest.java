package com.example.percentage.service;

import com.example.percentage.domain.ExternalPercentagePort;
import com.example.percentage.domain.HistoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class CalculationServiceTest {

    @Mock
    private HistoryPort historyPort;

    @Mock
    private ExternalPercentagePort percentagePort;

    @InjectMocks
    private CalculationService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCalculateSuccessfully() {
        when(percentagePort.getPercentage()).thenReturn(Mono.just(10.0));
        when(historyPort.logAsync(anyString(), anyMap(), any())).thenReturn(Mono.empty());

        StepVerifier.create(service.calculate(100.0, 200.0))
                .expectNextMatches(result -> result.getResult().compareTo(330.0) == 0)
                .verifyComplete();

        verify(percentagePort, times(1)).getPercentage();
        verify(historyPort, times(1)).logAsync(anyString(), anyMap(), any());
    }

    @Test
    void shouldPropagateErrorWhenPercentageServiceFails() {
        when(percentagePort.getPercentage()).thenReturn(Mono.error(new RuntimeException("Service Down")));

        StepVerifier.create(service.calculate(100.0, 200.0))
                .expectError(RuntimeException.class)
                .verify();

        verify(percentagePort, times(1)).getPercentage();
    }
}
