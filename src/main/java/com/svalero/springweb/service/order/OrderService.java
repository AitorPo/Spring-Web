package com.svalero.springweb.service.order;

import com.svalero.springweb.domain.Order;
import com.svalero.springweb.domain.Vendor;
import com.svalero.springweb.domain.dto.OrderDTO;

import java.util.Optional;
import java.util.Set;

public interface OrderService {
    Set<Order> findAll();
    Order addOrder(OrderDTO orderDTO);
    Order modifyOrder(long id, Order newOrder);
    void deleteOrder(long id);
    Order getOrder(long id);
    Optional<Order> findById(long id);
    Float getAveragePrice(long vendorId);
    Optional<Order> getOrderByVendor(long vendorId);
}
