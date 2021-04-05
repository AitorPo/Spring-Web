package com.svalero.springweb.repository;

import com.svalero.springweb.domain.Shop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ShopRepository extends CrudRepository<Shop, Long> {
    Set<Shop> findAll();
    Optional<Shop> findById(long id);
    Shop findByName(String name);
}
