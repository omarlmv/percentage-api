
package com.example.percentage.dto;

import java.time.Instant;

public record HistoryDto(Long id,
                         String endpoint,
                         String params,
                         String response,
                         Instant createdAt) { }
