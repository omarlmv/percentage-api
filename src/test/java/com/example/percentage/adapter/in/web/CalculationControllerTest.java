package com.example.percentage.adapter.in.web;

import com.example.percentage.model.CalcRequest;
import com.example.percentage.model.CalcResponse;
import com.example.percentage.service.CalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class CalculationControllerTest {

    @Mock
    private CalculationService calculationService;

    @InjectMocks
    private CalculationController calculationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCalculationResult() {
        CalcRequest request = new CalcRequest();
        request.setNum1(100.0);
        request.setNum2(200.0);

        CalcResponse response = new CalcResponse(330.0);

        when(calculationService.calculate(any(), any())).thenReturn(Mono.just(response));

        StepVerifier.create(calculationController.calculate(Mono.just(request), null)).expectNextMatches(res -> res.getStatusCode().is2xxSuccessful() && res.getBody() != null && res.getBody().getResult().compareTo(330.0) == 0).verifyComplete();

        verify(calculationService, times(1)).calculate(any(), any());
    }

    @Test
    void shouldHandleServiceError() {
        CalcRequest request = new CalcRequest();
        request.setNum1(100.0);
        request.setNum2(200.0);

        when(calculationService.calculate(any(), any())).thenReturn(Mono.error(new RuntimeException("Service Down")));

        StepVerifier.create(calculationController.calculate(Mono.just(request), null)).expectError(RuntimeException.class).verify();

        verify(calculationService, times(1)).calculate(any(), any());
    }
}
