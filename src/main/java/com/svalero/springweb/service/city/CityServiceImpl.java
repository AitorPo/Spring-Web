package com.svalero.springweb.service.city;


import com.svalero.springweb.domain.City;
import com.svalero.springweb.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CityServiceImpl implements CityService{

    @Autowired
    private CityRepository cityRepository;

    @Override
    public Set<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public Set<City> findByName(String name) {
        return cityRepository.findByName(name);
    }

    @Override
    public Optional<City> findById(long id) {
        return cityRepository.findById(id);
    }
}
