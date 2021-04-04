package com.svalero.springweb.repository;

import com.svalero.springweb.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Set<Order> findAll();
}
