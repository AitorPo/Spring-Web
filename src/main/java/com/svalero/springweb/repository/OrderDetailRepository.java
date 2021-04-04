package com.svalero.springweb.repository;

import com.svalero.springweb.domain.OrderDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long> {
    Set<OrderDetail> findAll();
}
