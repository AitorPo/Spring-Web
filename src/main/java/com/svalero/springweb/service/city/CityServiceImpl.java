package com.svalero.springweb.service.city;


import com.svalero.springweb.domain.City;
import com.svalero.springweb.exception.CityNotFoundException;
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

    @Override
    public City addCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public City modifyCity(long id, City newCity) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));
        newCity.setId(city.getId());
        return cityRepository.save(newCity);
    }

    @Override
    public void deleteCity(long id) {
        cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));
        cityRepository.deleteById(id);
    }

    @Override
    public City getCity(long id) {
        return cityRepository.getCity(id);
    }
}
