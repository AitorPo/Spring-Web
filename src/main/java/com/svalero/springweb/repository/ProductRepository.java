package com.svalero.springweb.repository;

import com.svalero.springweb.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Set<Product> findAll();
    Set<Product> findByCategory(String category);
    Product findByName(String name);
    Set<Product> findByNameAndPrice(String name, float price);
}
