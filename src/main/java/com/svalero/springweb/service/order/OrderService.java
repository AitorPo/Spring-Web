package com.svalero.springweb.service.order;

import com.svalero.springweb.domain.Order;
import com.svalero.springweb.domain.dto.OrderDTO;

import java.util.Set;

public interface OrderService {
    Set<Order> findAll();
    Order addOrder(OrderDTO orderDTO);
}
