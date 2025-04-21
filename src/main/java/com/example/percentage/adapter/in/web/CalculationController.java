
package com.example.percentage.adapter.in.web;

import com.example.percentage.api.CalculationApi;
import com.example.percentage.service.CalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class CalculationController implements CalculationApi {

    private final CalculationService service;

    @Override
    public Mono<ResponseEntity<com.example.percentage.model.CalcResponse>> calculate(
            Mono<com.example.percentage.model.CalcRequest> calcRequest,
            ServerWebExchange exchange
    ) {
        return calcRequest.flatMap(req ->
                service.calculate(req.getNum1(), req.getNum2())
                        .map(ResponseEntity::ok)
        );
    }
}

