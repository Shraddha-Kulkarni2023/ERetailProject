package com.example.order_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String customer_email;
    private Instant created_at = Instant.now();
    private String status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    private List<OrderLineEntity> lines = new ArrayList<>();

    public void addOrderItem(OrderLineEntity item) {

        item.setOrder(this);
        lines.add(item);
    }












}
