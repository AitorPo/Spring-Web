package com.svalero.springweb.service;

import com.svalero.springweb.domain.Order;
import com.svalero.springweb.domain.OrderDetail;
import com.svalero.springweb.domain.Product;
import com.svalero.springweb.domain.dto.OrderDTO;
import com.svalero.springweb.repository.OrderDetailRepository;
import com.svalero.springweb.repository.OrderRepository;
import com.svalero.springweb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Set<Order> findAll() {
        return orderRepository.findAll();
    }

    // Utilizamos el DTO para crear un pedido en nuestra BD
    @Override
    public Order addOrder(OrderDTO orderDTO) {
        String number = UUID.randomUUID().toString();
        Order newOrder = new Order();
        newOrder.setNumber(number);
        newOrder.setDate(LocalDateTime.now());
        newOrder.setPrice(orderDTO.getPrice());
        // Hasta que no hacemos .save el pedido no está introducido en nuestra BD y por tanto no tiene Id ni existe
        newOrder = orderRepository.save(newOrder);

        for (String productName : orderDTO.getProducts()) {
            Product product = productRepository.findByName(productName);
            OrderDetail detail = new OrderDetail();
            detail.setPrice(product.getPrice());
            detail.setQuantity(1);
            detail.setOrder(newOrder);
            detail.setProduct(product);
            newOrder.addDetail(detail);
            orderDetailRepository.save(detail);
        }
        return newOrder;
    }
}
