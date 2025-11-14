package com.example.order_service.service;

import com.example.order_service.entity.OrderEntity;
import com.example.order_service.entity.OrderLineEntity;
import com.example.order_service.entity.OutboxEvent;
import com.example.order_service.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.example.order_service.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;
    public OrderService(OrderRepository orderRepository, OutboxRepository outboxRepository, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public Long createOrder(String customerEmail, List<OrderLineEntity> orderLines) throws JsonProcessingException {

        OrderEntity order = new OrderEntity();
        order.setCustomer_email(customerEmail);
        orderLines.forEach(order :: addOrderItem);

        OrderEntity savedOrder = orderRepository.save(order);

        try {
            String payload = objectMapper.writeValueAsString(java.util.Map.of(
                    "orderId", savedOrder.getId(),
                    "customerEmail", savedOrder.getCustomer_email(),
                    "orderLines", savedOrder.getLines()
            ));

            OutboxEvent event = new OutboxEvent();
            event.setAggregateType("Order");
            event.setAggregateType(savedOrder.getId().toString());
            event.setType("OrderCreated");
            event.setPayload(payload);
            event.setCreatedAt(java.time.Instant.now());
            event.setPublished(false);
            event.setPublishedAt(null);
            outboxRepository.save(event);



        } catch (Exception e) {
             throw new RuntimeException(e);
        }

        return savedOrder.getId();

    }

}
