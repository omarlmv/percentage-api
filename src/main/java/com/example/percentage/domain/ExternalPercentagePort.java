
package com.example.percentage.domain;

import reactor.core.publisher.Mono;
import java.math.BigDecimal;

public interface ExternalPercentagePort {
    Mono<Double> getPercentage();
}
