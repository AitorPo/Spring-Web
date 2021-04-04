package com.svalero.springweb.controller;

import com.svalero.springweb.domain.Vendor;
import com.svalero.springweb.exception.ProductNotFoundException;
import com.svalero.springweb.exception.VendorNotFoundException;
import com.svalero.springweb.service.VendorService;
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
@Tag(name = "Vendors", description = "Listado de vendedores/as")

public class VendorController {

    private final Logger logger = LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private VendorService vendorService;

    @GetMapping(value = "/vendors", produces = "application/json")
    public ResponseEntity<Set<Vendor>> getVendors(){
        logger.info("Inicio de getVendors()");
        Set<Vendor> vendors = null;
        vendors = vendorService.findAll();
        logger.info("Fin de getVendors()");
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    @GetMapping(value = "/vendors/{id}", produces = "application/json")
    public ResponseEntity<Vendor> getVendor(@PathVariable("id") long id){
        Vendor vendor = vendorService.findById(id)
                .orElseThrow(() -> new VendorNotFoundException(id));

        return new ResponseEntity<>(vendor, HttpStatus.OK);
    }


}
