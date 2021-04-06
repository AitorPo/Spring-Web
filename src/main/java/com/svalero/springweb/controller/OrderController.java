package com.svalero.springweb.controller;



import com.svalero.springweb.domain.Order;
import com.svalero.springweb.domain.dto.OrderDTO;
import com.svalero.springweb.service.order.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@Tag(name = "Orders", description = "Listado de pedidos")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;


    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<Set<Order>> getOrders(){
        logger.info("Inicio de getOrders()");
        Set<Order> orders = null;
        orders = orderService.findAll();
        logger.info("Fin de getOrders()");

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }



    @PostMapping(value = "/orders", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Order> addOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderService.addOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
