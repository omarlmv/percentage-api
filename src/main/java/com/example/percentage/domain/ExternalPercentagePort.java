
package com.example.percentage.domain;

import reactor.core.publisher.Mono;

public interface ExternalPercentagePort {
    Mono<Double> getPercentage();
}
