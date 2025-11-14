package com.example.order_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "outbox_events")
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aggregateType;
    private String aggregateId;
    private String type;

    @Lob
    private String payload;
    private Instant createdAt = Instant.now();
    private Boolean published = false;
    private Instant publishedAt;

}
