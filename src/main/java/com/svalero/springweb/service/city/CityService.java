package com.svalero.springweb.service.city;

import com.svalero.springweb.domain.City;

import java.util.Optional;
import java.util.Set;

public interface CityService {
    Set<City> findAll();
    Set<City> findByName(String name);
    Optional<City> findById(long id);
}
