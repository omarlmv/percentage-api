
package com.example.percentage.adapter.out.jpa;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("history")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HistoryEntity {

    @Id
    private Long id;
    private String endpoint;
    private String params;
    private String response;
    private Instant createdAt;
}