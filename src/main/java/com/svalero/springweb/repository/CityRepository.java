package com.svalero.springweb.repository;

import com.svalero.springweb.domain.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {
    Set<City> findAll();
    Set<City> findByName(String name);
    Optional<City> findById(long id);

    @Query("FROM cities WHERE id = :id")
    City getCity(@Param("id") long id);
}
