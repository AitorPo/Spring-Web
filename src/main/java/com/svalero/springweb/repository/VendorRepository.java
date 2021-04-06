package com.svalero.springweb.repository;

import com.svalero.springweb.domain.Shop;
import com.svalero.springweb.domain.Vendor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface VendorRepository extends CrudRepository<Vendor, Long> {

    Set<Vendor> findAll();
    Optional<Vendor> findById(long id);
    Vendor findBySurname(String surname);
    Vendor findByName(String name);

    @Query("FROM vendors WHERE id = :id")
    Vendor getVendor(@Param("id") long id);
}
