package com.svalero.springweb.repository;

import com.svalero.springweb.domain.Vendor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface VendorRepository extends CrudRepository<Vendor, Long> {

    Set<Vendor> findAll();
    Optional<Vendor> findById(long id);
    Vendor findBySurname(String surname);
    Vendor findByName(String name);


}
