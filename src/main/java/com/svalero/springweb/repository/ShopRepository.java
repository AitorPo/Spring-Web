package com.svalero.springweb.repository;

import com.svalero.springweb.domain.Shop;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ShopRepository extends CrudRepository<Shop, Long> {
    Set<Shop> findAll();
    Optional<Shop> findById(long id);
    Shop findByName(String name);

    @Query("FROM shops WHERE id = :id")
    Shop getShop(@Param("id") long id);

}
