package com.example.order_service.entity;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;



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

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLines(List<OrderLineEntity> lines) {
        this.lines = lines;
    }

    public Long getId() {
        return id;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public String getStatus() {
        return status;
    }

    public List<OrderLineEntity> getLines() {
        return lines;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    private List<OrderLineEntity> lines = new ArrayList<>();

    public void addOrderItem(OrderLineEntity item) {

        item.setOrder(this);
        lines.add(item);
    }
}
