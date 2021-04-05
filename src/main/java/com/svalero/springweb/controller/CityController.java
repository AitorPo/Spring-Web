package com.svalero.springweb.controller;

import com.svalero.springweb.domain.City;
import com.svalero.springweb.exception.CityNotFoundException;
import com.svalero.springweb.service.city.CityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@Tag(name = "Cities", description = "Listado de ciudades")
public class CityController {

    Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    private CityService cityService;

    @GetMapping(value = "/cities", produces = "application/json")
    public ResponseEntity<Set<City>> getCities(){
        logger.info("Inicio de getCities()");
        Set<City> cities = null;
        cities = cityService.findAll();
        logger.info("Fin de getCities()");

        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping(value = "/cities/{id}", produces = "application/json")
    public ResponseEntity<City> getCity(@PathVariable("id") long id){
        City city = cityService.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));

        return new ResponseEntity<>(city, HttpStatus.OK);
    }
}
