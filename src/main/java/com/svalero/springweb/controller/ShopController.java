package com.svalero.springweb.controller;

import com.svalero.springweb.domain.Shop;
import com.svalero.springweb.exception.ShopNotFoundException;
import com.svalero.springweb.service.shop.ShopService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
@Tag(name = "Shops", description = "Listado de tiendas")
public class ShopController {

    private final Logger logger = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private ShopService shopService;

    @GetMapping(value = "/shops", produces = "application/json")
    public ResponseEntity<Set<Shop>> getShops() {
        logger.info("Inicio de getShops()");
        Set<Shop> shops = null;
        shops = shopService.findAll();
        logger.info("Fin de getShops()");

        return new ResponseEntity<>(shops, HttpStatus.OK);
    }

    @GetMapping(value = "/shops/{id}", produces = "application/json")
    public ResponseEntity<Shop> getShop(@PathVariable("id") long id){
        Shop shop = shopService.findById(id)
                .orElseThrow(() -> new ShopNotFoundException(id));
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }
}