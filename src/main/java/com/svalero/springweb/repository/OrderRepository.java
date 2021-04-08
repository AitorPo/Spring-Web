package com.svalero.springweb.repository;

import com.svalero.springweb.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Set<Order> findAll();

    @Query("FROM orders")
    Set<Order> getAllOrders();
    @Query("FROM orders WHERE id = :id")
    Order getOrder(@Param("id") long id);

    @Query("SELECT AVG(price) FROM orders")
    float getAveragePrice();

    @Query("SELECT AVG(price) FROM orders WHERE vendor_id = :vendor_id")
    Float getAveragePricePerVendor(@Param("vendor_id") long vendorId);

    @Query("FROM orders WHERE vendor_id = :vendor_id")
    Optional<Order> getOrderByVendor(@Param("vendor_id")long vendorId);
}
