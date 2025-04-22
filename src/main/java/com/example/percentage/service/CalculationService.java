
package com.example.percentage.service;

import com.example.percentage.domain.ExternalPercentagePort;
import com.example.percentage.domain.HistoryPort;
import com.example.percentage.model.CalcResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CalculationService {

    private final ExternalPercentagePort percentagePort;
    private final HistoryPort historyPort;

    public Mono<CalcResponse> calculate(Double n1, Double n2) {
        return percentagePort.getPercentage()
                .map(pct -> {
                    double sum = n1 + n2;
                    double result = sum + (sum * pct / 100);
                    return new CalcResponse(result);
                })
                .flatMap(resp -> historyPort
                        .logAsync("/api/v1/calc", Map.of("num1", n1, "num2", n2), resp)
                        .onErrorResume(e -> Mono.empty())
                        .thenReturn(resp));
    }
}
