package com.example.order_service.Controller;

import com.example.order_service.entity.OrderLineEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.order_service.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping

    public ResponseEntity<Long> createOrder(@RequestParam String customerEmail, @RequestBody List<OrderLineEntity> orderLineEntities) throws JsonProcessingException {


        Long id = orderService.createOrder(customerEmail, orderLineEntities);
        return ResponseEntity.ok(id);

    }







}
