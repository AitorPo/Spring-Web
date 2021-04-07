package com.svalero.springweb.repository;

import com.svalero.springweb.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Set<Order> findAll();

    @Query("FROM orders")
    Set<Order> getAllOrders();
    @Query("FROM orders WHERE id = :id")
    Order getOrder(@Param("id") long id);
}
