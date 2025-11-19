package com.example.order_service.Controller;

import com.example.order_service.entity.OrderLineEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.order_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order APIs", description = "Operations related to customer orders")

public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Operation(summary = "Create a new order",
    		   description = "Accepts customerEmail as query param and list of order line items")

    @PostMapping

    public ResponseEntity<Long> createOrder(@RequestParam String customerEmail, @RequestBody List<OrderLineEntity> orderLineEntities) throws JsonProcessingException {


        Long id = orderService.createOrder(customerEmail, orderLineEntities);
        return ResponseEntity.ok(id);

    }







}
