package com.svalero.springweb.controller;



import com.svalero.springweb.domain.Order;
import com.svalero.springweb.domain.dto.OrderDTO;
import com.svalero.springweb.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/orders", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Order> addOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderService.addOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
