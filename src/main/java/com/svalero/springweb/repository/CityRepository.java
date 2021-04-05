package com.svalero.springweb.repository;

import com.svalero.springweb.domain.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {
    Set<City> findAll();
    Set<City> findByName(String name);
}
